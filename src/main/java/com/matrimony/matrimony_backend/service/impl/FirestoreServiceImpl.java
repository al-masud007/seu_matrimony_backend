package com.matrimony.matrimony_backend.service.impl;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.matrimony.matrimony_backend.service.ImageService;
import com.matrimony.matrimony_backend.util.Base64Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class FirestoreServiceImpl implements ImageService {

    @Autowired(required = false)
    private Bucket storageBucket;
    
    @Override
    public String uploadImage(UUID userId, String base64Data, String imageType) {
        log.info("Uploading {} for user {}. Size: {} KB", 
                imageType, userId, Base64Utils.getFileSizeInKb(base64Data));

        if (storageBucket == null) {
            log.warn("Firebase Storage bucket not initialized. Using mock ID.");
            return "mock_doc_" + UUID.randomUUID().toString();
        }

        try {
            byte[] imageBytes = Base64Utils.decode(base64Data);
            String fileName = String.format("%s/%s_%s.jpg", userId, imageType, UUID.randomUUID());
            
            Blob blob = storageBucket.create(fileName, imageBytes, "image/jpeg");
            
            // For now, we return the name of the blob as the ID
            return blob.getName();
        } catch (Exception e) {
            log.error("Failed to upload image to Firebase: {}", e.getMessage());
            return "error_uploading";
        }
    }

    @Override
    public String getImage(String fileName) {
        if (storageBucket == null) {
            return "data:image/jpeg;base64,mock_base64_data";
        }

        try {
            Blob blob = storageBucket.get(fileName);
            if (blob == null) return null;
            
            byte[] content = blob.getContent();
            return Base64Utils.encode(content);
        } catch (Exception e) {
            log.error("Failed to fetch image from Firebase: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public void deleteImage(String fileName) {
        if (storageBucket == null) return;
        
        try {
            Blob blob = storageBucket.get(fileName);
            if (blob != null) {
                blob.delete();
            }
        } catch (Exception e) {
            log.error("Failed to delete image from Firebase: {}", e.getMessage());
        }
    }
}
