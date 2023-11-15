package com.ecommerce.dto.productoVersion;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ProductoVersionRegistroDTO(
    @NotBlank(message = "El nombre de la versión del producto es requerido.")
    @Pattern(regexp = "^[A-Za-zñÑ\\s0-9]{4,150}$", message = "El nombre de la versión del producto debe tener entre 4 y 150 caracteres.")
    String nombre,

    @NotBlank(message = "La descripción del producto es requerida.")
    @Pattern(regexp = "^[A-Za-zÑñ\\s0-9]{15,1000}$", message = "La descripción del producto debe tener entre 15 y 1000 caracteres.")
    String descripcion,

    @NotNull(message = "El precio de la versión del producto es requerido.")
    @DecimalMin(value = "0.01", message = "El precio de la versión del producto debe ser mayor que 0.01")
    BigDecimal precio,

    @DecimalMin(value = "0.01", message = "El precio de reventa de la versión del producto debe ser mayor que 0.01")
    BigDecimal precioReventa,

    @DecimalMin(value = "0.01", message = "El descuento de la versión del producto debe ser mayor que 0.01")
    BigDecimal descuento,

    @Min(value = 1, message = "El número de stock debe ser positivo.")
    Integer stock,

    @Min(value = 1, message = "El número de ventas debe ser positivo.")
    Integer ventas, 

    @Min(value = 1, message = "El número de pedidos debe ser positivo.")
    Integer pedidos
) {
    
}
