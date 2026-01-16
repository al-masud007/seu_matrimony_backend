package com.matrimony.matrimony_backend.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import com.google.cloud.storage.Bucket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Configuration
@Slf4j
public class FirebaseConfig {

    @PostConstruct
    public void initialize() {
        try {
            if (FirebaseApp.getApps().isEmpty()) {
                InputStream serviceAccount = new ClassPathResource("firebase-service-account.json").getInputStream();

                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .setStorageBucket("YOUR_PROJECT_ID.appspot.com") // Replace with your bucket name
                        .build();

                FirebaseApp.initializeApp(options);
                log.info("Firebase has been initialized");
            }
        } catch (IOException e) {
            log.error("Firebase initialization failed: {}", e.getMessage());
            // We don't throw exception here to allow the app to start even without Firebase in dev
        }
    }

    @Bean
    public Bucket storageBucket() {
        if (FirebaseApp.getApps().isEmpty()) {
            return null;
        }
        return StorageClient.getInstance().bucket();
    }
}
