package com.matrimony.matrimony_backend.dto.response;

import com.matrimony.matrimony_backend.enums.InterestStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class InterestResponse {
    private UUID id;
    private UUID senderId;
    private String senderName;
    private UUID receiverId;
    private String receiverName;
    private InterestStatus status;
    private LocalDateTime createdAt;
}
