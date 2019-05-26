package com.smartcity.exceptions;

import org.springframework.security.core.AuthenticationException;

public class InvalidJwtAuthenticationException extends AuthenticationException {

    private String name;

    public InvalidJwtAuthenticationException(String msg) {
        super(msg);
    }

    public InvalidJwtAuthenticationException(String msg, String name) {
        super(msg);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
