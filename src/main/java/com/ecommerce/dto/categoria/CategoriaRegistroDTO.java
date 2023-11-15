package com.ecommerce.dto.categoria;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CategoriaRegistroDTO(
    @NotBlank(message = "El nombre de la categoría es requerido.")
    @Pattern(regexp = "^[A-Za-zñÑ\s]{3,150}$", message = "El nombre de la categoría debe tener entre 3 y 150 caracteres.")
    String nombre
) {
    
}
