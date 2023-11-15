package com.ecommerce.dto.orden;

import java.math.BigDecimal;

import com.ecommerce.dto.productoVersion.ProductoVersionDetallesDTO;
import com.ecommerce.entity.Orden;

public record OrdenRespuestaDTO(Long id, BigDecimal precio, BigDecimal descuento, Integer cantidad, ProductoVersionDetallesDTO productoVersion) {
    public OrdenRespuestaDTO(Orden orden){
        this(
            orden.getId(),
            orden.getPrecio(),
            orden.getDescuento(),
            orden.getCantidad(),
            new ProductoVersionDetallesDTO(orden.getProductoVersion())
        );
    }
}
