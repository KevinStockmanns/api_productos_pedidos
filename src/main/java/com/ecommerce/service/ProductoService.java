package com.ecommerce.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.producto.ProductoActualizarDTO;
import com.ecommerce.dto.producto.ProductoCambiarPrecio;
import com.ecommerce.dto.producto.ProductoRegistroDTO;
import com.ecommerce.dto.productoVersion.ProductoVersionActualizarDTO;
import com.ecommerce.dto.productoVersion.ProductoVersionRegistroDTO;
import com.ecommerce.entity.Categoria;
import com.ecommerce.entity.HistorialPrecios;
import com.ecommerce.entity.Producto;
import com.ecommerce.entity.ProductoVersion;
import com.ecommerce.errors.ErrorIntegridad;
import com.ecommerce.repository.CategoriaRepository;
import com.ecommerce.repository.HistorialPrecioRepository;
import com.ecommerce.repository.ProductoRepository;
import com.ecommerce.repository.ProductoVersionRepository;
import com.ecommerce.utils.Utils;
import com.ecommerce.validations.historialPrecios.ValidarCrearRegistroHistorialPrecio;
import com.ecommerce.validations.producto.actualizar.ValidarActualizarProducto;
import com.ecommerce.validations.producto.crear.ValidarCrearProducto;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoVersionRepository productoVersionRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private HistorialPrecioRepository historialPrecioRepository;

    @Autowired
    private List<ValidarCrearProducto> validacionCreacion;

    @Autowired
    private List<ValidarActualizarProducto> validacionActualizar;

    @Autowired
    private List<ValidarCrearRegistroHistorialPrecio> validacionHistorialPrecio;

    public Producto crear(@Valid ProductoRegistroDTO datos) {
        validacionCreacion.forEach(v-> v.validar(datos));

        Producto producto = new Producto(datos);

        Categoria categoria = categoriaRepository.findByNombre(Utils.formatTitle(datos.categoria().nombre()));
        if(categoria == null)
            categoria = new Categoria(datos.categoria());

        producto.setCategoria(categoria);

        List<ProductoVersion> productoVersions = new ArrayList<>();
        List<HistorialPrecios> historialPrecios = new ArrayList<>();
        for(ProductoVersionRegistroDTO p:datos.versiones()){
            ProductoVersion productoVersion = new ProductoVersion(p);
            productoVersion.setProducto(producto);
            productoVersions.add(productoVersion);

            historialPrecios.add(new HistorialPrecios(productoVersion));
        }
        producto.setVersiones(productoVersions);
        producto.calcularStockPedidosVentas();

        categoriaRepository.save(categoria);
        productoRepository.save(producto);
        productoVersionRepository.saveAll(productoVersions);
        historialPrecioRepository.saveAll(historialPrecios);

        return producto;
    }

    @Transactional
    public Producto buscar(Long id) {
        Producto producto = productoRepository.getReferenceById(id);
        producto.actualizarVistas();
        return producto;
    }

    @Transactional
    public Page<Producto> listar(Pageable paginacion) {
        Page<Producto> productos = productoRepository.findByEstadoTrue(paginacion);
        productos.forEach(p-> p.actualizarVistas());
        return productos;
    }

    @Transactional
    public Producto actualizar(Long idProducto, @Valid ProductoActualizarDTO datos) {
        validacionActualizar.forEach(v-> v.validar(datos));
    
        Producto producto = productoRepository.getReferenceById(idProducto);
        producto.actualizar(datos);

        if(datos.categoria() != null){
            Categoria categoria;
            categoria = categoriaRepository.getReferenceById(datos.categoria().id());
            if(!categoria.getId().equals(datos.categoria().id()))
                throw new ErrorIntegridad("categoria.id", "El id de la categoría no cooresponde con el id de de la categoría del producto " + producto.getNombre());
            if(!datos.categoria().nombre().equalsIgnoreCase(categoria.getNombre()))
                categoria = categoriaRepository.save(new Categoria(datos.categoria()));
            producto.setCategoria(categoria);
        }
        
        if(datos.versiones() != null){
            List<ProductoVersion> productoVersiones = new ArrayList<>();
            int con = 0;
            for(ProductoVersionActualizarDTO version : datos.versiones()){
                boolean idEncontrado = false;
                for(ProductoVersion p : producto.getVersiones()){
                    if(version.id().equals(p.getId())){
                        idEncontrado = true;
                        break;
                    }
                }

                if(!idEncontrado){
                    throw new ErrorIntegridad("versiones[" + con + "].id", "El id de la versión no corresponde con ninguna versión del producto " + producto.getNombre());
                }
                con += 1;

                ProductoVersion productoVersion = productoVersionRepository.getReferenceById(version.id());
                productoVersion.actualizar(version);
                productoVersiones.add(productoVersion);
            }
            if(!productoVersiones.isEmpty()){
                producto.setVersiones(productoVersiones);
                producto.calcularStockPedidosVentas();
            }
        }
        return producto;
    }

    @Transactional
    public void desactivar(Long id) {
        Producto producto = productoRepository.getReferenceById(id);
        producto.setEstado(false);
    }


    public void eliminar(Long id) {
        Producto producto = productoRepository.getReferenceById(id);
        historialPrecioRepository.deleteAllByIdProducto(id);
        productoVersionRepository.deleteAll(producto.getVersiones());
        productoRepository.delete(producto);
    }

    @Transactional
    public Producto cambiarPrecio(Long idProducto, Boolean round, @Valid ProductoCambiarPrecio datos) {
        validacionHistorialPrecio.forEach(v-> v.validar(datos, idProducto));

        Producto producto = productoRepository.getReferenceById(idProducto);
        datos.versiones().forEach(d->{
            ProductoVersion productoVersion = productoVersionRepository.getReferenceById(d.id());

            if(d.precio() != null)
                productoVersion.setPrecio(round ? redondear(d.precio()) : d.precio());

            if(d.precioReventa() != null)
                productoVersion.setPrecioReventa(round ? redondear(d.precioReventa()) : d.precio());

            if(d.descuento() != null)
                productoVersion.setDescuento(round ? redondear(d.descuento()) : d.descuento());

            historialPrecioRepository.save(new HistorialPrecios(productoVersion));
        });
        return producto;
    }

    private BigDecimal redondear(BigDecimal precio) {
        BigDecimal ultimoDigito = precio.remainder(new BigDecimal(10));
        if(ultimoDigito.compareTo(new BigDecimal("4")) >= 0)
            precio = precio.add(new BigDecimal(10).subtract(ultimoDigito));
        else
            precio = precio.subtract(ultimoDigito);
        return precio;
    }
    
}
