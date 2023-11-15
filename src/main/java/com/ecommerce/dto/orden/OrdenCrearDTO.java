package com.ecommerce.dto.orden;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrdenCrearDTO(
    @NotNull(message = "El id de la versión del producto es requerido.")
    Long id,

    @NotNull(message = "La cantidad del producto es requerido.")
    @Min(value = 1, message = "La cantidad minima es 1.")
    Integer cantidad
) {
    
}
