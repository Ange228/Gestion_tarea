package com.rosaboutique.tiendarosa.dto;

import com.rosaboutique.tiendarosa.model.Producto;

import java.math.BigDecimal;

/**
 * DTO de SALIDA: lo que la API devuelve al cliente en formato JSON.
 * Incluye el "id" que ya fue asignado por la base de datos.
 */
public record ProductoResponse(
        Long id,
        String nombre,
        String marca,
        String categoria,
        BigDecimal precio,
        Integer stock
) {

    /** Convierte una entidad Producto en su representación de salida. */
    public static ProductoResponse desde(Producto p) {
        return new ProductoResponse(
                p.getId(), p.getNombre(), p.getMarca(),
                p.getCategoria(), p.getPrecio(), p.getStock());
    }
}
