package com.ecommerce.validations.producto.crear;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ecommerce.dto.producto.ProductoRegistroDTO;
import com.ecommerce.dto.productoVersion.ProductoVersionRegistroDTO;
import com.ecommerce.errors.ErrorLogicaNegocio;
import com.ecommerce.utils.Utils;

@Component
public class ProductoVersionSinRepetir implements ValidarCrearProducto {

    @Override
    public void validar(ProductoRegistroDTO datos) {
        List<String> nombres = new ArrayList<>();
        int con = 0;
        for(ProductoVersionRegistroDTO p :  datos.versiones()){
            if(nombres.contains(Utils.formatTitle(p.nombre())))
                throw new ErrorLogicaNegocio("versiones["+  con + "].nombre" , "No debe haber nombres de versiones del producto repetidos.");
            
            nombres.add(Utils.formatTitle(p.nombre()));
            con += 1;
        }
    }
    
}
