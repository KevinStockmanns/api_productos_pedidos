package com.ecommerce.dto.producto;

import java.util.List;

import com.ecommerce.dto.categoria.CategoriaActualizarDTO;
import com.ecommerce.dto.productoVersion.ProductoVersionActualizarDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ProductoActualizarDTO(
    @Pattern(regexp = "^[A-Za-zÑñ\\s0-9]{3,150}$", message = "El nombre del producto debe tener entre 3 y 150 caracteres.")
    String nombre, 
    Boolean estado, 

    @Valid
    CategoriaActualizarDTO categoria,

    @Valid
    @Size(min = 1, message = "Es necesario al menos una versión del producto para actualizar.")
    List<ProductoVersionActualizarDTO> versiones
) {
}
