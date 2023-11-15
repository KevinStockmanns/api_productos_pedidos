package com.ecommerce.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "historial_precios")
@Data @AllArgsConstructor @NoArgsConstructor
public class HistorialPrecios {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "producto_version_id")
    private ProductoVersion version;
    private LocalDate fecha;
    private BigDecimal precio;

    @Column(name = "precio_reventa")
    private BigDecimal precioReventa;
    private BigDecimal descuento;


    public HistorialPrecios(ProductoVersion productoVersion) {
        this.version = productoVersion;
        this.precio = productoVersion.getPrecio();
        this.precioReventa = productoVersion.getPrecioReventa();
        this.descuento = productoVersion.getDescuento();
        this.fecha = LocalDate.now();
        this.producto = productoVersion.getProducto();
    }
}
