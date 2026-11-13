package com.rosaboutique.tiendarosa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * MANEJO CENTRALIZADO DE ERRORES.
 *
 * @RestControllerAdvice "escucha" las excepciones de TODOS los controladores
 * y las traduce a respuestas JSON claras, en vez de mostrar un error feo.
 * Así, durante la exposición, un error de Postman se explica solo.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /** 404: el producto pedido no existe. */
    @ExceptionHandler(ProductoNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> noEncontrado(ProductoNoEncontradoException ex) {
        return construir(HttpStatus.NOT_FOUND, ex.getMessage(), null);
    }

    /** 400: el JSON enviado no cumple las validaciones (@NotBlank, @Positive...). */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> validacion(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(e -> errores.put(e.getField(), e.getDefaultMessage()));
        return construir(HttpStatus.BAD_REQUEST, "Datos inválidos", errores);
    }

    /** 400: el cuerpo no es un JSON válido (llave sin cerrar, precio "abc", etc.). */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> jsonIlegible(HttpMessageNotReadableException ex) {
        return construir(HttpStatus.BAD_REQUEST,
                "El cuerpo de la petición no es un JSON válido o tiene tipos incorrectos", null);
    }

    /** 400: por ejemplo GET /api/productos/abc (el id debería ser número). */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> tipoIncorrecto(MethodArgumentTypeMismatchException ex) {
        return construir(HttpStatus.BAD_REQUEST,
                "El parámetro '" + ex.getName() + "' tiene un valor inválido", null);
    }

    /** Arma el cuerpo JSON de error con un formato uniforme. */
    private ResponseEntity<Map<String, Object>> construir(HttpStatus estado, String mensaje, Object detalles) {
        Map<String, Object> cuerpo = new LinkedHashMap<>();
        cuerpo.put("fecha", LocalDateTime.now().toString());
        cuerpo.put("estado", estado.value());
        cuerpo.put("error", estado.getReasonPhrase());
        cuerpo.put("mensaje", mensaje);
        if (detalles != null) {
            cuerpo.put("detalles", detalles);
        }
        return ResponseEntity.status(estado).body(cuerpo);
    }
}
