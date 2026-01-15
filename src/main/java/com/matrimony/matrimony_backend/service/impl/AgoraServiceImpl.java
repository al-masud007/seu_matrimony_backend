package com.matrimony.matrimony_backend.service.impl;

import com.matrimony.matrimony_backend.entity.CallLog;
import com.matrimony.matrimony_backend.entity.User;
import com.matrimony.matrimony_backend.enums.CallStatus;
import com.matrimony.matrimony_backend.enums.CallType;
import com.matrimony.matrimony_backend.repository.CallLogRepository;
import com.matrimony.matrimony_backend.repository.UserRepository;
import com.matrimony.matrimony_backend.service.AgoraService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AgoraServiceImpl implements AgoraService {

    private final CallLogRepository callLogRepository;
    private final UserRepository userRepository;

    @Value("${app.agora-app-id}")
    private String appId;

    @Override
    public String generateToken(String channelName, int expirationTimeInSeconds) {
        // NOTE: This is a Mock token generator. 
        // In production, you would use RtcTokenBuilder with your App Certificate.
        return "mock_agora_token_for_channel_" + channelName;
    }

    @Override
    @Transactional
    public CallLog initiateCall(UUID receiverId, CallType callType) {
        User caller = getCurrentUser();
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        CallLog callLog = CallLog.builder()
                .caller(caller)
                .receiver(receiver)
                .callType(callType)
                .status(CallStatus.ONGOING)
                .startedAt(LocalDateTime.now())
                .build();

        return callLogRepository.save(callLog);
    }

    @Override
    @Transactional
    public CallLog endCall(UUID callId, int durationSeconds) {
        CallLog callLog = callLogRepository.findById(callId)
                .orElseThrow(() -> new RuntimeException("Call log not found"));

        callLog.setStatus(CallStatus.COMPLETED);
        callLog.setEndedAt(LocalDateTime.now());
        callLog.setDurationSeconds(durationSeconds);

        return callLogRepository.save(callLog);
    }

    @Override
    public List<CallLog> getCallHistory() {
        User user = getCurrentUser();
        return callLogRepository.findByUser(user);
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
