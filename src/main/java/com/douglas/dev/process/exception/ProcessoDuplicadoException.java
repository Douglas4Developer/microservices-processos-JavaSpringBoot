package com.douglas.dev.process.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProcessoDuplicadoException extends RuntimeException {

    public ProcessoDuplicadoException(String message) {
        super(message);
    }
}
