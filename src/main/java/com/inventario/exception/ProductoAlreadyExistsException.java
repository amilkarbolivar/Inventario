package com.inventario.exception;

public class ProductoAlreadyExistsException extends RuntimeException {
  public ProductoAlreadyExistsException(String message) {
    super(message);
  }
}