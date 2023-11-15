package com.ecommerce.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.ecommerce.dto.pedido.PedidoActualizarDTO;
import com.ecommerce.dto.pedido.PedidoCrearDTO;
import com.ecommerce.utils.Utils;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Table(name = "pedidos")
@Data @AllArgsConstructor @NoArgsConstructor
public class Pedido {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 150)
    private String propietario;

    @Column(length = 30)
    private String telefono;


    private String correo;

    @Column(name = "fecha_pedido")
    private LocalDateTime fechaPedido;

    @Column(name = "fecha_vendido")
    private LocalDateTime fechaVendido;

    private Boolean pendiente;
    private Boolean confirmado;
    private Boolean vendido;

    @OneToMany(mappedBy = "pedido")
    private List<Orden> ordenes;


    public Pedido(@Valid PedidoCrearDTO datos) {
        this.propietario = Utils.formatTitle(datos.propietario());
        this.telefono = datos.telefono();
        if(datos.correo() != null)
            this.correo = datos.correo();
        this.fechaPedido = LocalDateTime.now();
        this.pendiente = true;
        this.confirmado = false;
        this.vendido = false;
    }


    public void actualizar(PedidoActualizarDTO datos) {
        if(datos.propietario() != null)
            this.propietario = Utils.formatTitle(datos.propietario());
            
        if(datos.telefono() != null)
            this.telefono = datos.telefono();

        if(datos.correo() != null)
            this.correo = datos.correo();
    }
}
