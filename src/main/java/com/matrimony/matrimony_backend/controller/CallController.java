package com.matrimony.matrimony_backend.controller;

import com.matrimony.matrimony_backend.dto.response.ApiResponse;
import com.matrimony.matrimony_backend.entity.CallLog;
import com.matrimony.matrimony_backend.enums.CallType;
import com.matrimony.matrimony_backend.service.AgoraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/calls")
@RequiredArgsConstructor
public class CallController {

    private final AgoraService agoraService;

    @GetMapping("/token")
    public ResponseEntity<ApiResponse<String>> getToken(@RequestParam String channelName) {
        return ResponseEntity.ok(ApiResponse.success("Token generated", agoraService.generateToken(channelName, 3600)));
    }

    @PostMapping("/initiate/{receiverId}")
    public ResponseEntity<ApiResponse<CallLog>> initiateCall(@PathVariable UUID receiverId, @RequestParam CallType callType) {
        return ResponseEntity.ok(ApiResponse.success("Call initiated", agoraService.initiateCall(receiverId, callType)));
    }

    @PostMapping("/{callId}/end")
    public ResponseEntity<ApiResponse<CallLog>> endCall(@PathVariable UUID callId, @RequestParam int duration) {
        return ResponseEntity.ok(ApiResponse.success("Call ended", agoraService.endCall(callId, duration)));
    }

    @GetMapping("/history")
    public ResponseEntity<ApiResponse<List<CallLog>>> getCallHistory() {
        return ResponseEntity.ok(ApiResponse.success("Call history fetched", agoraService.getCallHistory()));
    }
}
