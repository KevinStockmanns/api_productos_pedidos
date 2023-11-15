package com.ecommerce.dto.productoVersion;

import java.math.BigDecimal;

import com.ecommerce.entity.ProductoVersion;

public record ProductoVersionDetallesDTO(Long id, String nombre, String descripcion, BigDecimal precio, BigDecimal precioReventa, BigDecimal descuento, Integer stock, Integer pedidos, Integer ventas) {

    public ProductoVersionDetallesDTO(ProductoVersion productoVersion){
        this(
            productoVersion.getId(), 
            productoVersion.getNombre(), 
            productoVersion.getDescripcion(), 
            productoVersion.getPrecio(), 
            productoVersion.getPrecioReventa(), 
            productoVersion.getDescuento(), 
            productoVersion.getStock(), 
            productoVersion.getPedidos(), 
            productoVersion.getVentas());
    }
    
}
