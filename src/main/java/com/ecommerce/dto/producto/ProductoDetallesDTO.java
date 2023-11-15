package com.ecommerce.dto.producto;

import java.util.List;

import com.ecommerce.dto.categoria.CategoriaRespuestaDTO;
import com.ecommerce.dto.productoVersion.ProductoVersionDetallesDTO;
import com.ecommerce.entity.Producto;

public record ProductoDetallesDTO(Long id, String nombre, Boolean estado, Integer vistas, Integer stock, Integer pedidos, Integer ventas, CategoriaRespuestaDTO categoria, List<ProductoVersionDetallesDTO> versiones) {

    public ProductoDetallesDTO(Producto producto) {
        this(
            producto.getId(), 
            producto.getNombre(), 
            producto.getEstado(), 
            producto.getVistas(), 
            producto.getStock(), 
            producto.getPedidos(), 
            producto.getVentas(), 
            new CategoriaRespuestaDTO(producto.getCategoria()),
            producto.getVersiones().stream().map(ProductoVersionDetallesDTO::new).toList());
    }
    
}
