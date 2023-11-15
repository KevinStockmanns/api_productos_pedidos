package com.ecommerce.validations.producto.actualizar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecommerce.dto.producto.ProductoActualizarDTO;
import com.ecommerce.errors.ErrorLogicaNegocio;
import com.ecommerce.repository.ProductoRepository;
import com.ecommerce.utils.Utils;

@Component
public class ActualizarProductoSinRepetir implements ValidarActualizarProducto {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public void validar(ProductoActualizarDTO datos) {
        if(productoRepository.existsByNombre(Utils.formatTitle(datos.nombre())))
            throw new ErrorLogicaNegocio("nombre", "El nombre de " + datos.nombre() + " ya está en uso.");
    }
    
}
