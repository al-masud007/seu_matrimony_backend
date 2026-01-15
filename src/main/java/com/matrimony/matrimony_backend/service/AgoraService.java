package com.matrimony.matrimony_backend.service;

import com.matrimony.matrimony_backend.entity.CallLog;
import com.matrimony.matrimony_backend.enums.CallType;

import java.util.List;
import java.util.UUID;

public interface AgoraService {
    String generateToken(String channelName, int expirationTimeInSeconds);
    CallLog initiateCall(UUID receiverId, CallType callType);
    CallLog endCall(UUID callId, int durationSeconds);
    List<CallLog> getCallHistory();
}
