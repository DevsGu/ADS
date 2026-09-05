package br.edu.ifce.retromarket.controllers.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

public class ResourceNotFoundException extends RuntimeException {
    
    public ResourceNotFoundException(String msg){
        super(msg);
    }
}
