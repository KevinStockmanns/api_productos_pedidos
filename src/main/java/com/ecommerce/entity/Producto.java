package com.ecommerce.entity;

import java.util.List;

import com.ecommerce.dto.producto.ProductoActualizarDTO;
import com.ecommerce.dto.producto.ProductoRegistroDTO;
import com.ecommerce.utils.Utils;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "productos")
@Data @AllArgsConstructor @NoArgsConstructor
public class Producto {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 150)
    private String nombre;
    private Integer stock;
    private Integer vistas;
    private Integer pedidos;
    private Integer ventas;
    private String imagen;
    private Boolean estado;
    
    @ManyToOne()
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @OneToMany(mappedBy = "producto")
    private List<ProductoVersion> versiones;



    public Producto(@Valid ProductoRegistroDTO datos) {
        this.nombre = Utils.formatTitle(datos.nombre());
        this.estado = (datos.estado() == null) ? true : datos.estado();
        this.stock = 0;
        this.ventas = 0;
        this.pedidos = 0;
        this.vistas = 0;
    }



    public void calcularStock() {
        this.stock = this.versiones.stream()
            .mapToInt(v-> v.getStock())
            .sum();
    }



    public void calcularPedidos() {
        this.pedidos = this.versiones.stream()
            .mapToInt(p-> p.getPedidos())
            .sum();
    }



    public void calcularVentas() {
        this.ventas = this.versiones.stream()
            .mapToInt(p-> p.getVentas())
            .sum();
    }

    public void calcularStockPedidosVentas(){
        calcularStock();
        calcularPedidos();
        calcularVentas();
    }



    public void actualizarVistas() {
        this.vistas = (this.vistas== null) ? 1 : this.vistas + 1;
    }



    public void actualizar(@Valid ProductoActualizarDTO datos) {
        if(datos.nombre() != null)
            this.nombre = Utils.formatTitle(datos.nombre());

        if(datos.estado() != null)
            this.estado = datos.estado();
    }
}
