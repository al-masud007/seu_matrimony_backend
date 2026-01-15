package com.matrimony.matrimony_backend.dto.response;

import com.matrimony.matrimony_backend.enums.MessageType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class MessageResponse {
    private UUID id;
    private UUID senderId;
    private String content;
    private MessageType messageType;
    private String imageDocId;
    private Boolean isRead;
    private LocalDateTime createdAt;
}
