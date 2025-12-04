package com.inventario.exception;

public class VentaNotFoundException extends RuntimeException {
    public VentaNotFoundException(String message) {
        super(message);
    }

    public VentaNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}