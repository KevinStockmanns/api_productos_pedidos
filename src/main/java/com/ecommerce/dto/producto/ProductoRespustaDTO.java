package com.ecommerce.dto.producto;

import java.util.List;

import com.ecommerce.dto.categoria.CategoriaRespuestaDTO;
import com.ecommerce.dto.productoVersion.ProductoVersionRespuestaDTO;
import com.ecommerce.entity.Producto;

public record ProductoRespustaDTO(Long id, String nombre, Boolean estado, Integer vistas, CategoriaRespuestaDTO categoria, List<ProductoVersionRespuestaDTO> versiones) {

    public ProductoRespustaDTO(Producto producto) {
        this(
            producto.getId(), 
            producto.getNombre(), 
            producto.getEstado(), 
            producto.getVistas(),
            new CategoriaRespuestaDTO(producto.getCategoria()), 
            producto.getVersiones().stream().map(ProductoVersionRespuestaDTO::new).toList());
    }
    
}
