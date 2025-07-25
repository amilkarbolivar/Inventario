package com.inventario.exception;

public class AdministradorAlreadyExistsException extends RuntimeException {
    public AdministradorAlreadyExistsException(String message) {
        super(message);
    }
}