package com.ecommerce.dto.pedido;

import java.util.List;

import com.ecommerce.dto.orden.OrdenActualizarDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PedidoActualizarDTO(
    @Pattern(regexp = "^[A-Za-zñÑ\\s]{3,150}$", message = "El nombre debe tener entre 3 y 150 letras.")
    String propietario,
    
    @Pattern(regexp = "^\\+\\d{2,4} \\d{2,4} \\d{6}$", message = "El telefono no es válido.")
    String telefono,

    @Email(message = "El correo no es válido.")
    String correo,

    @Size(min = 1, message = "Debe haber al menos una versión del producto.")
    @Valid
    List<OrdenActualizarDTO> ordenes
) {
    
}
