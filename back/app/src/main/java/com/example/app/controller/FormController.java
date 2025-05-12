package com.example.app.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.app.model.FormFactory;
import com.example.app.model.LoginRequest;
import com.example.app.service.FirestoreService;
import com.example.app.factory.AdminFormFactory;
import com.example.app.factory.FormField;
import com.example.app.factory.GuestFormFactory;



@RestController
@RequestMapping("/form")
@CrossOrigin(origins = "http://localhost:5173")
public class FormController {
    @Autowired
    private FirestoreService firestoreService;

    // Recibe un objeto JSON con la información de inicio de sesión (correo y contraseña)
    @PostMapping("/login")
        public Map<String, String> login(@RequestBody LoginRequest request) {

            // Se extraen las propiedades email y password del objeto LoginRequest
            String email = request.getEmail();
            String password = request.getPassword();

            if ("admin@email.com".equals(email) && "admin123".equals(password)) {
                return Map.of("role", "admin");
            } else if ("guest@email.com".equals(email) && "guest123".equals(password)) {
                return Map.of("role", "guest");
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
            }
        }



        @GetMapping("/{role}")
        public List<FormField> getForm(@PathVariable String role) {
           
            
            // Choose between static factories or dynamic factory based on your needs
            FormFactory factory;
            
            // Fallback to static factories if no data is found
            switch (role.toLowerCase()) {
                case "admin":
                    factory = new AdminFormFactory();
                    break;
                case "guest":
                    factory = new GuestFormFactory();
                    break;
                default:
                    throw new IllegalArgumentException("Rol no válido");
            }
        
            return factory.createFormFields();
        }
}
