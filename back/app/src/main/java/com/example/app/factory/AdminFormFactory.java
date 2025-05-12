package com.example.app.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.example.app.model.FormFactory;
import com.example.app.model.GenericField;
import com.example.app.service.FirestoreService;

public class AdminFormFactory implements FormFactory {
    public List<FormField> createFormFields() {
        String collection = "forms";
        String document = "admin";
        Map<String, Object> data;
        
        try {
            FirestoreService firestoreService = new FirestoreService();
            data = firestoreService.getDocument(collection, document);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException("Error al obtener los datos de Firestore", e);
        }
        List<FormField> fields = new ArrayList<>();
        
        
        // Process each field from the Firestore data
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            String fieldName = entry.getKey();
            Map<String, Object> fieldProperties = (Map<String, Object>) entry.getValue();
            
            String label = (String) fieldProperties.get("label");
            String type = (String) fieldProperties.get("type");
            boolean required = fieldProperties.containsKey("required") ? 
                                (boolean) fieldProperties.get("required") : false;
            
            fields.add(new GenericField(type, label, required));
        }
        
        return fields;
    }
}
