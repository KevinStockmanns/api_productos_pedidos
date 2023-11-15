package com.ecommerce.errors;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ecommerce.payload.ResponseWrapper;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class HandlerException {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseWrapper<Void>> tratarErrorJson(HttpMessageNotReadableException e){
        String message = "Ocurrio un problema al intentar leer el JSON.";
        System.out.println(e.getMessage());
        if(e.getMessage().startsWith("Required request body is missing"))
            message = "El JSON está vacio.";

        return ResponseEntity.badRequest().body(new ResponseWrapper<Void>(
            HttpStatus.BAD_REQUEST, 
            null, 
            List.of(new ErrorDTO(null, message)), 
            null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseWrapper<Void>> tratarErrorValidaciones(MethodArgumentNotValidException e){
        List<ErrorDTO> errores = e.getFieldErrors().stream().map(ErrorDTO::new).toList();
        return ResponseEntity.badRequest().body(new ResponseWrapper<Void>(
            HttpStatus.BAD_REQUEST, null, errores, null));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ResponseWrapper<Void>> tratarParametrosRequeridos(MissingServletRequestParameterException e){
        return ResponseEntity.badRequest().body(new ResponseWrapper<Void>(
            HttpStatus.BAD_REQUEST, 
            null, 
            List.of(new ErrorDTO(null, "El parámetro '" + e.getParameterName() + "' es requerido.")),
            null));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseWrapper<Void>> tratarArgumentoInvalido(IllegalArgumentException e){

        return ResponseEntity.badRequest().body(new ResponseWrapper<Void>(
            HttpStatus.BAD_REQUEST, 
            null, 
            List.of(new ErrorDTO(null, e.getMessage())),
            null));
    }


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseWrapper<Void>> tratarError404(EntityNotFoundException e){
        System.out.println(e.getMessage());
        String entidad = e.getMessage().substring(e.getMessage().lastIndexOf(".")+1);
        entidad = entidad.substring(0, entidad.indexOf(" "));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<Void>(
            HttpStatus.NOT_FOUND,
            null, 
            List.of(new ErrorDTO(null, entidad+" no encontrado/a en la base de datos.")),
            null));
    }

    @ExceptionHandler(ErrorIntegridad.class)
    public ResponseEntity<ResponseWrapper<Void>> tratarErrorIntegridad(ErrorIntegridad e){
        return ResponseEntity.badRequest().body(new ResponseWrapper<Void>(
            HttpStatus.BAD_REQUEST, 
            null, 
            List.of(new ErrorDTO(e.getCampo(), e.getMessage())),
            null));
    }
    
    @ExceptionHandler(ErrorLogicaNegocio.class)
    public ResponseEntity<ResponseWrapper<Void>> tratarErrorLogicaNegocio(ErrorLogicaNegocio e){

        return ResponseEntity.badRequest().body(new ResponseWrapper<Void>(
            HttpStatus.BAD_REQUEST, 
            null, 
            List.of(new ErrorDTO(e)), 
            null));
    }
}
