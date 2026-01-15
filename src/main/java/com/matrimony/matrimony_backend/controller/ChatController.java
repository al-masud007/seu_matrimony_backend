package com.matrimony.matrimony_backend.controller;

import com.matrimony.matrimony_backend.dto.request.MessageRequest;
import com.matrimony.matrimony_backend.dto.response.ApiResponse;
import com.matrimony.matrimony_backend.dto.response.ConversationResponse;
import com.matrimony.matrimony_backend.dto.response.MessageResponse;
import com.matrimony.matrimony_backend.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/conversations")
    public ResponseEntity<ApiResponse<List<ConversationResponse>>> getConversations() {
        return ResponseEntity.ok(ApiResponse.success("Conversations fetched", chatService.getConversations()));
    }

    @PostMapping("/conversations/{otherUserId}")
    public ResponseEntity<ApiResponse<ConversationResponse>> getOrCreateConversation(@PathVariable UUID otherUserId) {
        return ResponseEntity.ok(ApiResponse.success("Conversation ready", chatService.getOrCreateConversation(otherUserId)));
    }

    @PostMapping("/conversations/{id}/messages")
    public ResponseEntity<ApiResponse<MessageResponse>> sendMessage(@PathVariable UUID id, @RequestBody MessageRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Message sent", chatService.sendMessage(id, request)));
    }

    @GetMapping("/conversations/{id}/messages")
    public ResponseEntity<ApiResponse<Page<MessageResponse>>> getMessages(
            @PathVariable UUID id,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success("Messages fetched", chatService.getMessages(id, pageable)));
    }
}
