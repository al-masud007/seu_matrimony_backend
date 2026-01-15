package com.matrimony.matrimony_backend.service;

import java.util.UUID;

public interface ImageService {
    String uploadImage(UUID userId, String base64Data, String imageType);
    String getImage(String firestoreDocId);
    void deleteImage(String firestoreDocId);
}
