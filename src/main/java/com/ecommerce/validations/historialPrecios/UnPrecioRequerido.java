package com.ecommerce.validations.historialPrecios;

import org.springframework.stereotype.Component;

import com.ecommerce.dto.producto.ProductoCambiarPrecio;
import com.ecommerce.errors.ErrorLogicaNegocio;

@Component
public class UnPrecioRequerido implements ValidarCrearRegistroHistorialPrecio{

    @Override
    public void validar(ProductoCambiarPrecio datos, Long idProducto) {
        datos.versiones().forEach(versiones->{
            if(versiones.precio() == null && versiones.precioReventa() == null && versiones.descuento() == null)
                throw new ErrorLogicaNegocio("", "Al menos debe tener un dato del precio");
        });
    }
    
}
