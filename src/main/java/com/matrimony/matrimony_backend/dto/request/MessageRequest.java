package com.matrimony.matrimony_backend.dto.request;

import com.matrimony.matrimony_backend.enums.MessageType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class MessageRequest {
    private String content;
    private MessageType messageType = MessageType.TEXT;
    private String base64Image; // For image messages
}
