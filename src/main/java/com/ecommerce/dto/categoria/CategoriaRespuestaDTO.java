package com.ecommerce.dto.categoria;

import com.ecommerce.entity.Categoria;

public record CategoriaRespuestaDTO(Long id, String nombre) {
    public CategoriaRespuestaDTO(Categoria categoria){
        this(categoria.getId(), 
        categoria.getNombre());
    }
    
}
