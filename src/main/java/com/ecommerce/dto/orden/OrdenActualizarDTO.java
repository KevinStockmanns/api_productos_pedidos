package com.ecommerce.dto.orden;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrdenActualizarDTO(
    @NotNull(message = "El id de la versión del producto es requerido.")
    Long id,

    @NotNull(message = "La cantidad del producto es requerido.")
    @Min(value = 0, message = "La cantidad minima es 0.")
    Integer cantidad
) {
    
}
