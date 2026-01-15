package com.matrimony.matrimony_backend.service.impl;

import com.matrimony.matrimony_backend.dto.request.InterestRequest;
import com.matrimony.matrimony_backend.dto.response.InterestResponse;
import com.matrimony.matrimony_backend.entity.Interest;
import com.matrimony.matrimony_backend.entity.User;
import com.matrimony.matrimony_backend.enums.InterestStatus;
import com.matrimony.matrimony_backend.repository.InterestRepository;
import com.matrimony.matrimony_backend.repository.UserRepository;
import com.matrimony.matrimony_backend.service.InterestService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InterestServiceImpl implements InterestService {

    private final InterestRepository interestRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public InterestResponse sendInterest(InterestRequest request) {
        User sender = getCurrentUser();
        User receiver = userRepository.findById(request.getReceiverId())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        if (sender.getId().equals(receiver.getId())) {
            throw new RuntimeException("You cannot send interest to yourself");
        }

        interestRepository.findBySenderAndReceiver(sender, receiver).ifPresent(i -> {
            throw new RuntimeException("Interest already sent");
        });

        Interest interest = Interest.builder()
                .sender(sender)
                .receiver(receiver)
                .status(InterestStatus.PENDING)
                .build();

        return mapToResponse(interestRepository.save(interest));
    }

    @Override
    @Transactional
    public InterestResponse acceptInterest(UUID interestId) {
        Interest interest = interestRepository.findById(interestId)
                .orElseThrow(() -> new RuntimeException("Interest not found"));

        User currentUser = getCurrentUser();
        if (!interest.getReceiver().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Unauthorized to accept this interest");
        }

        interest.setStatus(InterestStatus.ACCEPTED);
        return mapToResponse(interestRepository.save(interest));
    }

    @Override
    @Transactional
    public InterestResponse rejectInterest(UUID interestId) {
        Interest interest = interestRepository.findById(interestId)
                .orElseThrow(() -> new RuntimeException("Interest not found"));

        User currentUser = getCurrentUser();
        if (!interest.getReceiver().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Unauthorized to reject this interest");
        }

        interest.setStatus(InterestStatus.REJECTED);
        return mapToResponse(interestRepository.save(interest));
    }

    @Override
    public List<InterestResponse> getSentInterests() {
        User sender = getCurrentUser();
        return interestRepository.findBySender(sender).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<InterestResponse> getReceivedInterests() {
        User receiver = getCurrentUser();
        return interestRepository.findByReceiver(receiver).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private InterestResponse mapToResponse(Interest interest) {
        return InterestResponse.builder()
                .id(interest.getId())
                .senderId(interest.getSender().getId())
                .senderName(interest.getSender().getFullName())
                .receiverId(interest.getReceiver().getId())
                .receiverName(interest.getReceiver().getFullName())
                .status(interest.getStatus())
                .createdAt(interest.getCreatedAt())
                .build();
    }
}
