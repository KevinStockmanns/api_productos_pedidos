package com.ecommerce.dto.pedido;

import java.time.LocalDateTime;
import java.util.List;

import com.ecommerce.dto.orden.OrdenRespuestaDTO;
import com.ecommerce.entity.Pedido;

public record PedidoRespuestaDTO(Long id, String propietario, String telefono, String correo, LocalDateTime fechaPedido, LocalDateTime fechaVendido, Boolean pendiente, Boolean confirmado, Boolean vendido, List<OrdenRespuestaDTO> ordenes) {

    public PedidoRespuestaDTO(Pedido pedido) {
        this(
            pedido.getId(), 
            pedido.getPropietario(), 
            pedido.getTelefono(), 
            pedido.getCorreo(), 
            pedido.getFechaPedido(), 
            pedido.getFechaVendido(), 
            pedido.getPendiente(), 
            pedido.getConfirmado(), 
            pedido.getVendido(), 
            pedido.getOrdenes().stream().map(OrdenRespuestaDTO::new).toList()
        );
    }
    
}
