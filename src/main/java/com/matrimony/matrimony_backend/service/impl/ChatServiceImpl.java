package com.matrimony.matrimony_backend.service.impl;

import com.matrimony.matrimony_backend.dto.request.MessageRequest;
import com.matrimony.matrimony_backend.dto.response.ConversationResponse;
import com.matrimony.matrimony_backend.dto.response.MessageResponse;
import com.matrimony.matrimony_backend.entity.Conversation;
import com.matrimony.matrimony_backend.entity.Message;
import com.matrimony.matrimony_backend.entity.User;
import com.matrimony.matrimony_backend.enums.MessageType;
import com.matrimony.matrimony_backend.repository.ConversationRepository;
import com.matrimony.matrimony_backend.repository.MessageRepository;
import com.matrimony.matrimony_backend.repository.UserRepository;
import com.matrimony.matrimony_backend.service.ChatService;
import com.matrimony.matrimony_backend.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;

    @Override
    public List<ConversationResponse> getConversations() {
        User user = getCurrentUser();
        return conversationRepository.findByUser(user).stream()
                .map(c -> mapToConversationResponse(c, user))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ConversationResponse getOrCreateConversation(UUID otherUserId) {
        User currentUser = getCurrentUser();
        User otherUser = userRepository.findById(otherUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Conversation conversation = conversationRepository.findBetweenUsers(currentUser, otherUser)
                .orElseGet(() -> {
                    Conversation newConv = Conversation.builder()
                            .user1(currentUser)
                            .user2(otherUser)
                            .build();
                    return conversationRepository.save(newConv);
                });

        return mapToConversationResponse(conversation, currentUser);
    }

    @Override
    @Transactional
    public MessageResponse sendMessage(UUID conversationId, MessageRequest request) {
        User currentUser = getCurrentUser();
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Conversation not found"));

        String imageDocId = null;
        if (request.getMessageType() == MessageType.IMAGE && request.getBase64Image() != null) {
            imageDocId = imageService.uploadImage(currentUser.getId(), request.getBase64Image(), "chat_image");
        }

        Message message = Message.builder()
                .conversation(conversation)
                .sender(currentUser)
                .content(request.getContent())
                .messageType(request.getMessageType())
                .imageDocId(imageDocId)
                .build();

        Message savedMessage = messageRepository.save(message);

        // Update conversation last message
        conversation.setLastMessage(request.getMessageType() == MessageType.IMAGE ? "Image" : request.getContent());
        conversation.setLastMessageAt(LocalDateTime.now());
        conversationRepository.save(conversation);

        return mapToMessageResponse(savedMessage);
    }

    @Override
    public Page<MessageResponse> getMessages(UUID conversationId, Pageable pageable) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Conversation not found"));
        
        return messageRepository.findByConversationOrderByCreatedAtDesc(conversation, pageable)
                .map(this::mapToMessageResponse);
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private ConversationResponse mapToConversationResponse(Conversation c, User currentUser) {
        User otherUser = c.getUser1().getId().equals(currentUser.getId()) ? c.getUser2() : c.getUser1();
        return ConversationResponse.builder()
                .id(c.getId())
                .otherUserId(otherUser.getId())
                .otherUserName(otherUser.getFullName())
                .otherUserPhoto(otherUser.getProfilePhotoUrl())
                .lastMessage(c.getLastMessage())
                .lastMessageAt(c.getLastMessageAt())
                .createdAt(c.getCreatedAt())
                .build();
    }

    private MessageResponse mapToMessageResponse(Message m) {
        return MessageResponse.builder()
                .id(m.getId())
                .senderId(m.getSender().getId())
                .content(m.getContent())
                .messageType(m.getMessageType())
                .imageDocId(m.getImageDocId())
                .isRead(m.getIsRead())
                .createdAt(m.getCreatedAt())
                .build();
    }
}
