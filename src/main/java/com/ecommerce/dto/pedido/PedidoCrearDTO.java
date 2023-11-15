package com.ecommerce.dto.pedido;

import java.util.List;

import com.ecommerce.dto.orden.OrdenCrearDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PedidoCrearDTO(
    @NotBlank(message = "El nombre es requerido.")
    @Pattern(regexp = "^[A-Za-zñÑ\\s]{3,150}$", message = "El nombre debe tener entre 3 y 150 letras.")
    String propietario,
    
    @NotBlank(message = "El telefono es requerido.")
    @Pattern(regexp = "^\\+\\d{2,4} \\d{2,4} \\d{6}$", message = "El telefono no es válido.")
    String telefono,

    @Email(message = "El formato del correo no es válido.")
    String correo,

    @NotNull(message = "Las versiones del producto son requeridas.")
    @Size(min = 1, message = "Debe haber al menos una versión del producto.")
    @Valid
    List<OrdenCrearDTO> ordenes
) {
    
}
