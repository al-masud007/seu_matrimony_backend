package com.matrimony.matrimony_backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhotoUploadRequest {
    
    @NotBlank(message = "Base64 image data is required")
    private String base64Data;
    
    private String imageType; // e.g., "profile_photo" or "id_document"
}
