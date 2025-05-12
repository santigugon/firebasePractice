package com.example.app.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class FirestoreService {

    public void saveDocument(String collectionName, String documentId, Map<String, Object> data) {
        Firestore firestore = FirestoreClient.getFirestore();
        DocumentReference docRef = firestore.collection(collectionName).document(documentId);
        docRef.set(data);
    }
    
    public Map<String, Object> getDocument(String collectionName, String documentId) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        DocumentReference docRef = firestore.collection(collectionName).document(documentId);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        
        if (document.exists()) {
            return document.getData();
        } else {
            return null;
        }
    }
    
    public List<QueryDocumentSnapshot> getAllDocuments(String collectionName) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = firestore.collection(collectionName).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        return documents;
    }
}