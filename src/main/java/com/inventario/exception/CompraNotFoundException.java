package com.inventario.exception;

public class CompraNotFoundException extends RuntimeException {
    public CompraNotFoundException(String message) {
        super(message);
    }

    public CompraNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}