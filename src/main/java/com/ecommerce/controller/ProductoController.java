package com.ecommerce.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ecommerce.dto.producto.ProductoActualizarDTO;
import com.ecommerce.dto.producto.ProductoCambiarPrecio;
import com.ecommerce.dto.producto.ProductoDetallesDTO;
import com.ecommerce.dto.producto.ProductoRegistroDTO;
import com.ecommerce.dto.producto.ProductoRespustaDTO;
import com.ecommerce.entity.Producto;
import com.ecommerce.payload.ResponseWrapper;
import com.ecommerce.service.ProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @PostMapping("/producto")
    @Operation(summary = "Registrar Producto", description = "Endpoint para registrar un producto, incluye la validación de datos,generador de id y guardado en la base de datos.")
    public ResponseEntity<ResponseWrapper<ProductoRespustaDTO>> crearProducto(
        @RequestBody @Valid ProductoRegistroDTO datos,
        UriComponentsBuilder uriComponentsBuilder
    ){
        Producto producto = productoService.crear(datos);
        URI uri = uriComponentsBuilder.path(("/producto/{id}")).buildAndExpand(producto.getId()).toUri();
        return ResponseEntity.created(uri).body(new ResponseWrapper<ProductoRespustaDTO>(
            HttpStatus.CREATED, 
            "Producto creado con éxito.", 
            null, 
            new ProductoRespustaDTO(producto)));
    }

    @GetMapping("/producto/{id}")
    @Operation(summary = "Obtener Producto")
    public ResponseEntity<ResponseWrapper<ProductoDetallesDTO>> obtenderProducto(
        @PathVariable Long id
    ){
        Producto producto = productoService.buscar(id);
        return ResponseEntity.ok().body(new ResponseWrapper<ProductoDetallesDTO>(
            HttpStatus.OK, 
            "Producto encontrado con éxito.", 
            null,
            new ProductoDetallesDTO(producto)));
    }

    @GetMapping("/productos")
    @Operation(summary = "Listado de Productos", description = "Obtiene la lista de productos disponibles.")
    public ResponseEntity<ResponseWrapper<Page<ProductoRespustaDTO>>> listarProductos(
        @Parameter(name = "page", description = "Número de la página", example = "0", required = false)
        @RequestParam(defaultValue = "0") int page,

        @Parameter(name = "size", description = "Cantidad de elementos que devuelve la consulta. El maximo es de 10.", example = "10", required = false)
        @RequestParam(defaultValue = "10") int size
    ){
        size = (size > 10) ? 10 : size;
        Pageable paginacion = PageRequest.of(page, size, Sort.by(Sort.Order.desc("vistas")));

        Page<Producto> productos = productoService.listar(paginacion);
        Page<ProductoRespustaDTO> productosDTO = productos.map(ProductoRespustaDTO::new);
        return ResponseEntity.ok().body(new ResponseWrapper<Page<ProductoRespustaDTO>>(
            HttpStatus.OK, 
            "Listado de productos con éxito.", 
            null, 
            productosDTO));
    }

    @PutMapping("/producto/{id}")
    @Operation(summary = "Actualizar Producto")
    public ResponseEntity<ResponseWrapper<ProductoRespustaDTO>> actualizarProducto(
        @PathVariable Long id,
        @RequestBody @Valid ProductoActualizarDTO datos
    ){
        Producto producto = productoService.actualizar(id, datos);
        return ResponseEntity.ok().body(new ResponseWrapper<ProductoRespustaDTO>(
            HttpStatus.OK, 
            "Producto actualizado con éxito.", 
            null, 
            new ProductoRespustaDTO(producto)));
    }

    @DeleteMapping("/producto/{id}")
    @Operation(summary = "Desactivar Producto")
    public ResponseEntity<Void> desactivarProducto(
        @PathVariable Long id,

        @Parameter(name = "delete", required = false, description = "El parametro sirve para eliminar o desactivar el producto, en caso de ser 'true' se elimina permanentemente, si es 'false' se inhabilita el producto.")
        @RequestParam(defaultValue = "false") Boolean delete
    ){
        if(delete)
            productoService.eliminar(id);
        else
            productoService.desactivar(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/producto/{id}/precios")
    @Operation(summary = "Subir Precio del Producto")
    public ResponseEntity<ResponseWrapper<ProductoDetallesDTO>> cambiarPrecioProducto(
        @PathVariable Long id,

        @Parameter(name = "round", required = false, description = "El parametro sirve para indicar si se redondea o no el precio, en caso de ser 'true' si se redondea en caso opuesto 'false' no se redondea.")
        @RequestParam(defaultValue = "true") Boolean round,
        @RequestBody @Valid ProductoCambiarPrecio datos
    ){
        Producto producto = productoService.cambiarPrecio(id, round, datos);
        return ResponseEntity.ok().body(new ResponseWrapper<ProductoDetallesDTO>(
            HttpStatus.OK, 
            null, 
            null, 
            new ProductoDetallesDTO(producto)));
    }
}
