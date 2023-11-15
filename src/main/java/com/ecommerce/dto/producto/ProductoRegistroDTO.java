package com.ecommerce.dto.producto;

import java.util.List;

import com.ecommerce.dto.categoria.CategoriaRegistroDTO;
import com.ecommerce.dto.productoVersion.ProductoVersionRegistroDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ProductoRegistroDTO(
    @NotBlank(message = "El nombre del producto es requerido.")
    @Pattern(regexp = "^[A-Za-zÑñ\\s0-9]{3,150}$", message = "El nombre del producto debe tener entre 3 y 150 caracteres.")
    String nombre,

    Boolean estado,

    @Valid @NotNull(message = "La categoría del producto es requerida.")
    CategoriaRegistroDTO categoria,

    @Valid @NotNull(message = "Las versiones del producto son requeridas.")
    @Size(min = 1, message = "Se necesita al menos una version del producto.")
    @NotNull(message = "Las versiones del procuto son requeridas.")
    List<ProductoVersionRegistroDTO> versiones
) {
    
}
