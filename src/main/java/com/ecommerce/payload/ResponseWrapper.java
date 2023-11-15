package com.ecommerce.payload;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.ecommerce.errors.ErrorDTO;

import lombok.Getter;

@Getter
public class ResponseWrapper<T> {
    private Boolean ok;
    private Integer status;
    private String message;
    private List<ErrorDTO> errores;
    private T body;

    public ResponseWrapper(HttpStatus status, String message, List<ErrorDTO> errores, T body){
        this.ok = status.is2xxSuccessful();
        this.status = status.value();
        this.message = message;
        this.errores = (errores == null) ? List.of() : errores;
        this.body = body;
    }
    
}
