package com.ecommerce.errors;

import org.springframework.validation.FieldError;

public record ErrorDTO(String campo, String error) {

    public ErrorDTO(ErrorGenerico e) {
        this(e.getCampo(), e.getMessage());
    }

    public ErrorDTO(FieldError e){
        this(e.getField(), e.getDefaultMessage());
    }
    
}
