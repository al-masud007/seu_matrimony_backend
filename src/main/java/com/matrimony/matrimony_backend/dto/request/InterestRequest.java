package com.matrimony.matrimony_backend.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class InterestRequest {
    @NotNull(message = "Receiver ID is required")
    private UUID receiverId;
}
