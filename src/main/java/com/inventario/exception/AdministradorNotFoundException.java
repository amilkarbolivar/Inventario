package com.inventario.exception;

public class AdministradorNotFoundException extends RuntimeException {
    public AdministradorNotFoundException(String message) {
        super(message);
    }

    public AdministradorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
