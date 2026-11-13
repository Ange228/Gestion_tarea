package com.rosaboutique.tiendarosa.repository;

import com.rosaboutique.tiendarosa.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * CAPA DE REPOSITORIO (acceso a datos).
 *
 * Solo declaramos una interfaz: Spring Data genera la implementación sola.
 * JpaRepository ya trae save(), findAll(), findById(), deleteById()...
 * sin escribir una línea de SQL.
 */
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Spring deduce la consulta SQL a partir del nombre del método:
    // SELECT * FROM productos WHERE LOWER(categoria) = LOWER(?)
    List<Producto> findByCategoriaIgnoreCase(String categoria);
}
