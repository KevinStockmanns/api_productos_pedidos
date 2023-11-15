package com.ecommerce.entity;

import java.math.BigDecimal;

import com.ecommerce.dto.productoVersion.ProductoVersionActualizarDTO;
import com.ecommerce.dto.productoVersion.ProductoVersionRegistroDTO;
import com.ecommerce.utils.Utils;

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
@Table(name = "producto_versiones")
@Data @AllArgsConstructor @NoArgsConstructor
public class ProductoVersion {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 150)
    private String nombre;

    @Column(length = 1000)
    private String descripcion;

    private BigDecimal precio;

    @Column(name = "precio_reventa")
    private BigDecimal precioReventa;

    private BigDecimal descuento;

    private Integer stock;
    private Integer pedidos;
    private Integer ventas;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    
    public ProductoVersion(ProductoVersionRegistroDTO datos) {
        this.nombre = Utils.formatTitle(datos.nombre());
        this.descripcion = Utils.formatText(datos.descripcion());
        this.precio = datos.precio();
        this.precioReventa = (datos.precioReventa() == null) ? BigDecimal.valueOf(0L) : datos.precioReventa();
        this.descuento = (datos.descuento() == null) ? BigDecimal.valueOf(0L) : datos.descuento();
        this.stock = (datos.stock() == null) ? 0 : datos.stock();
        this.pedidos = (datos.pedidos() == null) ? 0 : datos.pedidos();
        this.ventas = (datos.ventas() == null) ? 0 : datos.ventas();
    }


    public void actualizar(ProductoVersionActualizarDTO datos) {
        if(datos.nombre() != null)
            this.nombre = Utils.formatTitle(datos.nombre());

        if(datos.descripcion() != null)
            this.descripcion = Utils.formatText(datos.descripcion());

        if(datos.precio() != null)
            this.precio = datos.precio();

        if(datos.precioReventa() != null)
            this.precioReventa = datos.precioReventa();

        if(datos.descuento() != null)
            this.descuento = datos.descuento();

        if(datos.stock() != null)
            this.stock = datos.stock();

        if(datos.pedidos() != null)
            this.pedidos = datos.pedidos();

        if(datos.ventas() != null)
            this.ventas = datos.ventas();
    }


    public void actualizarStock(Integer cantidad) {
        int newStock = this.stock - cantidad;

        this.stock = (newStock < 0) ? 0 : newStock;
    }


    public void aumentarVentas(Integer cantidad) {
        this.ventas += cantidad;
    }


    public void aumentarPedido(Integer cantidad) {
        this.pedidos += cantidad;
    }
}
