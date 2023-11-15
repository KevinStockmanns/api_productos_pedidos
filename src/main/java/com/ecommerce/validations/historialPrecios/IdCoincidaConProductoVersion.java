package com.ecommerce.validations.historialPrecios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecommerce.dto.producto.ProductoCambiarPrecio;
import com.ecommerce.dto.productoVersion.ProductoVersionCambiarPrecio;
import com.ecommerce.entity.Producto;
import com.ecommerce.entity.ProductoVersion;
import com.ecommerce.errors.ErrorLogicaNegocio;
import com.ecommerce.repository.ProductoRepository;

@Component
public class IdCoincidaConProductoVersion implements ValidarCrearRegistroHistorialPrecio{

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public void validar(ProductoCambiarPrecio datos, Long idProducto) {
        Producto producto = productoRepository.getReferenceById(idProducto);
        int con = 0;
        for(ProductoVersionCambiarPrecio version : datos.versiones()){
            boolean idPresente = false;
            for(ProductoVersion productoVersion : producto.getVersiones()){
                if(productoVersion.getId().equals(version.id())){
                    idPresente = true;
                    break;
                }
            }
            
            if(!idPresente)
                throw new ErrorLogicaNegocio("versiones["+ con +"].id", "El id ingresado no corresponde con ningún id de las versiones del producto " + producto.getNombre());

            con += 1;
        };
    }
    
}
