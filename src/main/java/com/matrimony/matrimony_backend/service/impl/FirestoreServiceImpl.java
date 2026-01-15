package com.matrimony.matrimony_backend.service.impl;

import com.matrimony.matrimony_backend.service.ImageService;
import com.matrimony.matrimony_backend.util.Base64Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
public class FirestoreServiceImpl implements ImageService {

    // NOTE: This is a Mock implementation until firebase-service-account.json is provided
    
    @Override
    public String uploadImage(UUID userId, String base64Data, String imageType) {
        log.info("Mock uploading {} for user {}. Size: {} KB", 
                imageType, userId, Base64Utils.getFileSizeInKb(base64Data));
        
        // Return a dummy Firestore document ID
        return "mock_doc_" + UUID.randomUUID().toString();
    }

    @Override
    public String getImage(String firestoreDocId) {
        log.info("Mock fetching image with ID: {}", firestoreDocId);
        return "data:image/jpeg;base64,mock_base64_data";
    }

    @Override
    public void deleteImage(String firestoreDocId) {
        log.info("Mock deleting image with ID: {}", firestoreDocId);
    }
}
