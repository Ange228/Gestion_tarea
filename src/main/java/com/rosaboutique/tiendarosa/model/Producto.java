package com.rosaboutique.tiendarosa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

/**
 * CAPA DE MODELO (dominio).
 *
 * Representa un producto de la tienda y, gracias a @Entity, también es una
 * tabla "productos" en la base de datos. Cada atributo es una columna.
 *
 * Tiene 6 campos (el requisito pedía mínimo 4):
 *   id, nombre, marca, categoria, precio, stock
 */
@Entity
@Table(name = "productos")
public class Producto {

    // Clave primaria; la base de datos la genera sola (1, 2, 3...)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 60)
    private String marca;

    @Column(nullable = false, length = 40)
    private String categoria;

    // BigDecimal (y no double) es lo correcto para dinero: evita errores de redondeo
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(nullable = false)
    private Integer stock;

    // JPA exige un constructor vacío
    protected Producto() {
    }

    public Producto(String nombre, String marca, String categoria, BigDecimal precio, Integer stock) {
        this.nombre = nombre;
        this.marca = marca;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
    }

    /**
     * Reemplaza los datos del producto (lo usa el PUT).
     * El id NO cambia: identifica al registro. Al estar la entidad "gestionada"
     * por JPA dentro de una transacción, basta con modificarla y Hibernate
     * genera el UPDATE automáticamente al terminar el método del servicio.
     */
    public void actualizar(String nombre, String marca, String categoria, BigDecimal precio, Integer stock) {
        this.nombre = nombre;
        this.marca = marca;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getMarca() { return marca; }
    public String getCategoria() { return categoria; }
    public BigDecimal getPrecio() { return precio; }
    public Integer getStock() { return stock; }
}
