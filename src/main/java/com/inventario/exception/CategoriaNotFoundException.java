package com.inventario.exception;

public class CategoriaNotFoundException extends RuntimeException {
    public CategoriaNotFoundException(String message) {
        super(message);
    }

    public CategoriaNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}