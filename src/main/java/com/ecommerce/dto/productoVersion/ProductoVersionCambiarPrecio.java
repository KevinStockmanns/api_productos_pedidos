package com.ecommerce.dto.productoVersion;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record ProductoVersionCambiarPrecio(
    @NotNull(message = "El id de la versión es requerido.")
    Long id,

    @DecimalMin(value = "0.01", message = "El precio de la versión del producto debe ser mayor que 0.01")
    BigDecimal precio,

    @DecimalMin(value = "0.01", message = "El precio de reventa de la versión del producto debe ser mayor que 0.01")
    BigDecimal precioReventa,

    @DecimalMin(value = "0.00", message = "El descuento de la versión del producto debe ser positivo.")
    BigDecimal descuento
) {
    
}
