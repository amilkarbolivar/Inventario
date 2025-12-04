package com.inventario.exception;

public class CategoriaAlreadyExistsException extends RuntimeException {
    public CategoriaAlreadyExistsException(String message) {
        super(message);
    }
}