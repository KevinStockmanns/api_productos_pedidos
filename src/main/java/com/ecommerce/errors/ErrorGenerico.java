package com.ecommerce.errors;

import lombok.Getter;

@Getter
public class ErrorGenerico extends RuntimeException{
    private String campo;

    public ErrorGenerico(String campo, String message){
        super(message);
        this.campo = campo;
    }
}
