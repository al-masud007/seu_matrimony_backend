package com.matrimony.matrimony_backend.service;

import com.matrimony.matrimony_backend.dto.request.MessageRequest;
import com.matrimony.matrimony_backend.dto.response.ConversationResponse;
import com.matrimony.matrimony_backend.dto.response.MessageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ChatService {
    List<ConversationResponse> getConversations();
    ConversationResponse getOrCreateConversation(UUID otherUserId);
    MessageResponse sendMessage(UUID conversationId, MessageRequest request);
    Page<MessageResponse> getMessages(UUID conversationId, Pageable pageable);
}
