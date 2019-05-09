package com.smartcity.exceptions;

public class RecordExistsException extends RuntimeException {

    private String name;

    public RecordExistsException(String message) {
        super(message);
    }

    public RecordExistsException(String message, String name) {
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
