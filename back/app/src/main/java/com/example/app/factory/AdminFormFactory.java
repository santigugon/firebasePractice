package com.example.app.factory;

import java.util.List;

import com.example.app.model.FormFactory;
import com.example.app.model.GenericField;

public class AdminFormFactory implements FormFactory {
    public List<FormField> createFormFields() {
        // Se crea y retorna una lista inmutable utilizando List.of()
        // La lista contiene dos objetos de tipo GenericField que representan los campos de un formulario

        return List.of(
            // Se crea un campo de tipo 'text' con la etiqueta 'Username' y que es obligatorio (true)
            new GenericField("text", "Username", true),

            // Se crea un campo de tipo 'password' con la etiqueta 'Admin Key' y que también es obligatorio (true)
            new GenericField("password", "Admin Key", true)
        );
    }
}
