package com.ecommerce.entity;

import java.math.BigDecimal;

import com.ecommerce.dto.orden.OrdenCrearDTO;

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
@Table(name = "ordenes")
@Data @AllArgsConstructor @NoArgsConstructor
public class Orden {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "producto_version_id")
    private ProductoVersion productoVersion;

    private Integer cantidad;

    private BigDecimal precio;
    private BigDecimal descuento;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    public Orden(ProductoVersion productoVersion, OrdenCrearDTO datos) {
        this.producto = productoVersion.getProducto();
        this.productoVersion = productoVersion;
        this.cantidad = datos.cantidad();
        this.precio = productoVersion.getPrecio();
        this.descuento = productoVersion.getDescuento();
    }
}
