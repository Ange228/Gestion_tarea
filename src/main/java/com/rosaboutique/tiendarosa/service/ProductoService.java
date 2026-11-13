package com.rosaboutique.tiendarosa.service;

import com.rosaboutique.tiendarosa.dto.ProductoRequest;
import com.rosaboutique.tiendarosa.dto.ProductoResponse;
import com.rosaboutique.tiendarosa.exception.ProductoNoEncontradoException;
import com.rosaboutique.tiendarosa.model.Producto;
import com.rosaboutique.tiendarosa.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * CAPA DE SERVICIO (lógica de negocio).
 *
 * El controlador NO habla con la base de datos directamente: le pide cosas
 * al servicio, y el servicio usa el repositorio. Esa separación hace el
 * código más fácil de probar y de cambiar.
 *
 * Flujo completo de una petición:
 *   Cliente (Postman) -> Controller -> Service -> Repository -> Base de datos
 */
@Service
public class ProductoService {

    private final ProductoRepository repository;

    // Inyección de dependencias por constructor: Spring nos pasa el repositorio.
    // Es la forma recomendada (permite campos final y facilita las pruebas).
    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    }

    /** Lista todos los productos; si llega categoría, filtra por ella. */
    @Transactional(readOnly = true)
    public List<ProductoResponse> listar(String categoria) {
        List<Producto> productos = (categoria == null || categoria.isBlank())
                ? repository.findAll()
                : repository.findByCategoriaIgnoreCase(categoria.trim());

        return productos.stream().map(ProductoResponse::desde).toList();
    }

    /** Busca un producto por id; si no existe lanza una excepción propia (404). */
    @Transactional(readOnly = true)
    public ProductoResponse obtenerPorId(Long id) {
        Producto producto = repository.findById(id)
                .orElseThrow(() -> new ProductoNoEncontradoException(id));
        return ProductoResponse.desde(producto);
    }

    /** Convierte el DTO en entidad, lo guarda y devuelve el resultado con su id. */
    @Transactional
    public ProductoResponse crear(ProductoRequest datos) {
        Producto nuevo = new Producto(
                datos.nombre().trim(),
                datos.marca().trim(),
                datos.categoria().trim(),
                datos.precio(),
                datos.stock());

        return ProductoResponse.desde(repository.save(nuevo));
    }

    /**
     * PUT: reemplaza TODOS los datos de un producto existente.
     * Primero se busca (si no existe -> 404) y luego se modifica.
     * No hace falta llamar a save(): al ser @Transactional, JPA detecta el
     * cambio en la entidad y lo guarda solo ("dirty checking").
     */
    @Transactional
    public ProductoResponse actualizar(Long id, ProductoRequest datos) {
        Producto existente = repository.findById(id)
                .orElseThrow(() -> new ProductoNoEncontradoException(id));

        existente.actualizar(
                datos.nombre().trim(),
                datos.marca().trim(),
                datos.categoria().trim(),
                datos.precio(),
                datos.stock());

        return ProductoResponse.desde(existente);
    }

    /** DELETE: elimina un producto; si no existe lanza 404. */
    @Transactional
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new ProductoNoEncontradoException(id);
        }
        repository.deleteById(id);
    }
}
