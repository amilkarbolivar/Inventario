package com.inventario.exception;

public class ClienteAlreadyExistException extends RuntimeException {
    public ClienteAlreadyExistException(String message) {
        super(message);
    }
}
