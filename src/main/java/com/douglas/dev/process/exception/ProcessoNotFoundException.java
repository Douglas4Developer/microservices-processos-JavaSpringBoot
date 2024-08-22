package com.douglas.dev.process.exception;

public class ProcessoNotFoundException extends RuntimeException {

    public ProcessoNotFoundException(String message) {
        super(message);
    }
}