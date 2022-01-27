package com.example.pixeltek.REST.service;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper=false)
public class UmsException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private final HttpStatus status;
    private String message;
    public UmsException(HttpStatus status, String message){
        this.status=status;
        this.message=message;
    }

}
