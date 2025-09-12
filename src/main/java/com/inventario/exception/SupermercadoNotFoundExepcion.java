package com.inventario.exception;

public class SupermercadoNotFoundExepcion extends RuntimeException {
    public SupermercadoNotFoundExepcion(String message) {
        super(message);
    }

    public SupermercadoNotFoundExepcion(String message, Throwable cause){
      super(message, cause);
    }
}
