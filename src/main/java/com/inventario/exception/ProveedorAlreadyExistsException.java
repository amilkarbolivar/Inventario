package com.inventario.exception;

public class ProveedorAlreadyExistsException extends RuntimeException {
    public ProveedorAlreadyExistsException(String message) {
        super(message);
    }
}