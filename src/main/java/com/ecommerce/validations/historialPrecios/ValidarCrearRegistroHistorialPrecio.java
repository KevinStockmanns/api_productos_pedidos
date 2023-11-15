package com.ecommerce.validations.historialPrecios;

import com.ecommerce.dto.producto.ProductoCambiarPrecio;

public interface ValidarCrearRegistroHistorialPrecio {
    public void validar(ProductoCambiarPrecio datos, Long idProducto);
}
