package com.smartcity.exceptions;

public class DbOperationException extends RuntimeException {

    private String name;

    public DbOperationException(String message) {
        super(message);
    }

    public DbOperationException(String message, String name) {
        super(message);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
