package com.rosaboutique.tiendarosa.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

/**
 * DTO de ENTRADA (Data Transfer Object): lo que el cliente envía en el POST.
 *
 * ¿Por qué un DTO y no usar directamente la entidad?
 *  - El cliente NO debe decidir el "id" (lo genera la base de datos).
 *  - Así podemos validar la entrada sin ensuciar la entidad.
 *  - Si mañana cambia la tabla, el contrato de la API no tiene por qué cambiar.
 *
 * Las anotaciones (@NotBlank, @Positive...) se comprueban automáticamente
 * cuando el controlador usa @Valid. Si algo falla, responde 400 Bad Request.
 */
public record ProductoRequest(

        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100, message = "El nombre no puede superar 100 caracteres")
        String nombre,

        @NotBlank(message = "La marca es obligatoria")
        @Size(max = 60, message = "La marca no puede superar 60 caracteres")
        String marca,

        @NotBlank(message = "La categoría es obligatoria")
        @Size(max = 40, message = "La categoría no puede superar 40 caracteres")
        String categoria,

        @NotNull(message = "El precio es obligatorio")
        @Positive(message = "El precio debe ser mayor que 0")
        BigDecimal precio,

        @NotNull(message = "El stock es obligatorio")
        @Min(value = 0, message = "El stock no puede ser negativo")
        Integer stock
) {
}
