package com.iffomko.voiceAssistant.security.jwt;


import org.springframework.http.HttpStatus;

public class JwtAuthenticationException extends RuntimeException {
    private final String message;
    private final HttpStatus status;

    public JwtAuthenticationException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
