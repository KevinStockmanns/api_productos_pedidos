package com.ecommerce.dto.productoVersion;

import com.ecommerce.entity.ProductoVersion;

public record ProductoVersionRespuestaDTO(Long id, String nombre, String descripcion) {
    public ProductoVersionRespuestaDTO(ProductoVersion productoVersion){
        this(productoVersion.getId(), productoVersion.getNombre(), productoVersion.getDescripcion());
    }
}
