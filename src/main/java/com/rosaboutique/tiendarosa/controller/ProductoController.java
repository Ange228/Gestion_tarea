package com.rosaboutique.tiendarosa.controller;

import com.rosaboutique.tiendarosa.dto.ProductoRequest;
import com.rosaboutique.tiendarosa.dto.ProductoResponse;
import com.rosaboutique.tiendarosa.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * CAPA DE CONTROLADOR (expone la API HTTP).
 *
 * Su único trabajo: recibir la petición, delegar en el servicio y devolver
 * la respuesta con el código HTTP correcto. NO contiene lógica de negocio.
 *
 * @RestController = @Controller + @ResponseBody: lo que retornan los métodos
 *                   se convierte automáticamente en JSON.
 * @RequestMapping fija la ruta base: todos los endpoints cuelgan de /api/productos
 *
 * Endpoints:
 *   GET  /api/productos            -> lista todos (opcional ?categoria=Labiales)
 *   GET  /api/productos/{id}       -> un producto por id
 *   POST /api/productos            -> crea un producto nuevo
 *   PUT  /api/productos/{id}       -> reemplaza los datos de un producto
 *   DELETE /api/productos/{id}     -> elimina un producto
 */
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    /**
     * GET /api/productos
     * @RequestParam(required = false): el filtro ?categoria=... es opcional.
     */
    @GetMapping
    public List<ProductoResponse> listar(@RequestParam(required = false) String categoria) {
        return service.listar(categoria);
    }

    /**
     * GET /api/productos/3
     * @PathVariable toma el "3" de la URL y lo pasa como parámetro.
     */
    @GetMapping("/{id}")
    public ProductoResponse obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    /**
     * POST /api/productos
     * @RequestBody: convierte el JSON del cuerpo en un ProductoRequest.
     * @Valid:       ejecuta las validaciones del DTO antes de entrar al método.
     *
     * Devuelve 201 Created + cabecera Location con la URL del recurso nuevo,
     * que es la convención REST correcta para un POST que crea algo.
     */
    @PostMapping
    public ResponseEntity<ProductoResponse> crear(@Valid @RequestBody ProductoRequest datos) {
        ProductoResponse creado = service.crear(datos);

        URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creado.id())
                .toUri();

        return ResponseEntity.status(HttpStatus.CREATED).location(ubicacion).body(creado);
    }

    /**
     * PUT /api/productos/3
     * Reemplaza por completo el producto 3 con el JSON recibido.
     * Devuelve 200 OK con el producto ya actualizado (o 404 si no existe).
     */
    @PutMapping("/{id}")
    public ProductoResponse actualizar(@PathVariable Long id, @Valid @RequestBody ProductoRequest datos) {
        return service.actualizar(id, datos);
    }

    /**
     * DELETE /api/productos/3
     * Elimina el producto 3. Devuelve 204 No Content (éxito sin cuerpo)
     * o 404 si no existe.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
