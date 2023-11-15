package com.ecommerce.dto.producto;

import java.util.List;

import com.ecommerce.dto.productoVersion.ProductoVersionCambiarPrecio;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProductoCambiarPrecio(
    @Valid
    @NotNull(message = "Las versiones son requeridas.")
    @Size(min = 1, message = "Es necesario al menos una versión del producto.")
    List<ProductoVersionCambiarPrecio> versiones
) {
    
}
