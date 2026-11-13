package com.rosaboutique.tiendarosa.exception;

/**
 * Excepción propia: se lanza cuando se pide un producto que no existe.
 * El GlobalExceptionHandler la convierte en una respuesta 404.
 */
public class ProductoNoEncontradoException extends RuntimeException {

    public ProductoNoEncontradoException(Long id) {
        super("No existe un producto con id " + id);
    }
}
