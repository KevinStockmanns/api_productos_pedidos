package com.ecommerce.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.orden.OrdenActualizarDTO;
import com.ecommerce.dto.orden.OrdenCrearDTO;
import com.ecommerce.dto.pedido.PedidoActualizarDTO;
import com.ecommerce.dto.pedido.PedidoCrearDTO;
import com.ecommerce.entity.Orden;
import com.ecommerce.entity.Pedido;
import com.ecommerce.entity.ProductoVersion;
import com.ecommerce.errors.ErrorIntegridad;
import com.ecommerce.repository.OrdenRepository;
import com.ecommerce.repository.PedidoRepository;
import com.ecommerce.repository.ProductoVersionRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class PedidoService {

    @Autowired
    private ProductoVersionRepository productoVersionRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private OrdenRepository ordenRepository;

    @Transactional
    public Pedido crear(@Valid PedidoCrearDTO datos) {
        Pedido pedido = new Pedido(datos);

        List<Orden> ordenes = new ArrayList<>();
        for(OrdenCrearDTO vDatos : datos.ordenes()){
            Orden orden = new Orden(productoVersionRepository.getReferenceById(vDatos.id()), vDatos);
            orden.setPedido(pedido);
            orden.getProductoVersion().aumentarPedido(vDatos.cantidad());
            ordenes.add(orden);
        }
        pedido.setOrdenes(ordenes);

        pedidoRepository.save(pedido);
        ordenRepository.saveAll(ordenes);
        return pedido;
    }

    public void eliminar(Long idPedido) {
        Pedido pedido = pedidoRepository.getReferenceById(idPedido);
        ordenRepository.deleteAll(pedido.getOrdenes());
        pedidoRepository.delete(pedido);
    }

    @Transactional
    public void cancelar(Long idPedido) {
        Pedido pedido = pedidoRepository.getReferenceById(idPedido);
        pedido.setPendiente(false);
        pedido.setConfirmado(false);
    }

    public Page<Pedido> listar(String estado, Pageable paginacion) {
        if(estado.equals("pendientes"))
            return pedidoRepository.findByPendienteTrue(paginacion);
        else if (estado.equals("confirmados"))
            return pedidoRepository.findByConfirmadoTrue(paginacion);
        else if (estado.equals("vendidos"))
            return pedidoRepository.findByVendidoTrue(paginacion);
            
        return pedidoRepository.findByPendienteFalseAndConfirmadoFalseAndVendidoFalse(paginacion);
    }

    @Transactional
    public void confirmar(Long id) {
        Pedido pedido = pedidoRepository.getReferenceById(id);
        if(!pedido.getPendiente())
            pedido.setPendiente(true);
        pedido.setConfirmado(true);
    }

    @Transactional
    public void vender(Long id) {
        Pedido pedido = pedidoRepository.getReferenceById(id);
        pedido.setPendiente(false);
        if(!pedido.getConfirmado())
            pedido.setConfirmado(true);
        pedido.setVendido(true);
        pedido.setFechaVendido(LocalDateTime.now());

        for(Orden o : pedido.getOrdenes()){
            ProductoVersion productoVersion = o.getProductoVersion();
            productoVersion.actualizarStock(o.getCantidad());
            productoVersion.aumentarVentas(o.getCantidad());
            o.getProducto().calcularStockPedidosVentas();;
        }
    }

    @Transactional
    public Pedido actualizar(Long id, PedidoActualizarDTO datos) {
        Pedido pedido = pedidoRepository.getReferenceById(id);
        pedido.actualizar(datos);

        if(datos.ordenes() != null){
            List<Orden> ordenes = new ArrayList<>();
            int con = 0;
            for(OrdenActualizarDTO d : datos.ordenes()){
                boolean idPresente = false;
                for(Orden o : pedido.getOrdenes()){
                    if(d.id().equals(o.getId()))
                        idPresente = true;
                        break;
                }
                if(idPresente)
                    throw new ErrorIntegridad("ordenes["+con+"].id", "El id ingresado de la orden no coincide con las ordenes del pedido hecho.");
                con += 1;

                Orden orden = ordenRepository.getReferenceById(d.id());
                if(d.cantidad() == 0){
                    ordenRepository.delete(orden);
                }else{
                    orden.setCantidad(d.cantidad());
                    ordenes.add(orden);
                }
            };

            pedido.setOrdenes(ordenes);
        }
        return pedido;
    }
    
}
