package com.ecommerce.validations.producto.crear;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecommerce.dto.producto.ProductoRegistroDTO;
import com.ecommerce.errors.ErrorLogicaNegocio;
import com.ecommerce.repository.ProductoRepository;
import com.ecommerce.utils.Utils;

@Component
public class CrearProductoSinRepetir implements ValidarCrearProducto {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public void validar(ProductoRegistroDTO datos) {
        if(productoRepository.existsByNombre(Utils.formatTitle(datos.nombre())))
            throw new ErrorLogicaNegocio("nombre", "El nombre ingresado del producto '" + datos.nombre() + "' ya está en uso.");
    }
    
}
