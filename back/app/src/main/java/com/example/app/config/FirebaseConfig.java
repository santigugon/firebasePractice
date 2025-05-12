package com.example.app.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void initialize() {
        try {
            // Check if Firebase app is already initialized
            if (FirebaseApp.getApps().isEmpty()) {
                // Use ClassPathResource to access the file from resources folder
                ClassPathResource serviceAccountResource = 
                    new ClassPathResource("firebase.json");
                
                InputStream serviceAccountStream = serviceAccountResource.getInputStream();
                
                FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccountStream))
                    .setDatabaseUrl("https://jwt-token-5a98e-default-rtdb.firebaseio.com")
                    .build();
                
                FirebaseApp.initializeApp(options);
                System.out.println("Firebase application has been initialized");
            }
        } catch (IOException e) {
            System.err.println("Error initializing Firebase: " + e.getMessage());
            e.printStackTrace();
        }
    }
}