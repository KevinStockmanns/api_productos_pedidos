package com.ecommerce.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import com.ecommerce.dto.pedido.PedidoActualizarDTO;
import com.ecommerce.dto.pedido.PedidoCrearDTO;
import com.ecommerce.dto.pedido.PedidoRespuestaDTO;
import com.ecommerce.entity.Pedido;
import com.ecommerce.payload.ResponseWrapper;
import com.ecommerce.service.PedidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Pedidos")
public class PedidoController {
    
    @Autowired
    private PedidoService pedidoService;

    @PostMapping("/pedido")
    @Operation(summary = "Registrar Pedido", description = "Registra un pedido en la base de datos, incluye la verificación de los mismos. Donde un pedido puede tener uno o varias ordenes.")
    public ResponseEntity<ResponseWrapper<PedidoRespuestaDTO>> crearPedido(
        @RequestBody @Valid PedidoCrearDTO datos,
        UriComponentsBuilder uriComponentsBuilder
    ){
        Pedido pedido = pedidoService.crear(datos);
        URI uri = uriComponentsBuilder.path("pedido/{id}").buildAndExpand(pedido.getId()).toUri();
        return ResponseEntity.created(uri).body(new ResponseWrapper<PedidoRespuestaDTO>(
            HttpStatus.CREATED, 
            "Pedido registrado con éxito", 
            null, 
            new PedidoRespuestaDTO(pedido)));
    }


    @DeleteMapping("/pedido/{id}")
    @Operation(summary = "Cancelar Pedido")
    public ResponseEntity<Void> cancelarPedido(
        @PathVariable Long idPedido,

        @Parameter(name = "delete", example = "false", required = false, description = "Elimina o cancela el pedido, en caso de ser true se elimina permanentemente, si es false se desactiva o cancela el pedido. (valores aceptados: 'false', 'true')")
        @RequestParam(defaultValue = "false") Boolean delete
    ){
        if(delete)
            pedidoService.eliminar(idPedido);
        else
            pedidoService.cancelar(idPedido);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pedidos")
    @Operation(summary = "Listado de Pedidos")
    public ResponseEntity<ResponseWrapper<Page<PedidoRespuestaDTO>>> listarPedidos(
        @Parameter(name = "estado", example = "pendientes", required = true, description = "Este parametro sirve para saber que pedidos listar, los valores permitidos son: 'pendientes', 'confirmados', 'vendidos', 'cancelados'.")
        @RequestParam(required = true) String estado,
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "10") Integer size
    ){
        estado = estado.trim().toLowerCase();
        if(!estado.equals("pendientes") && !estado.equals("confirmados") && !estado.equals("vendidos") && !estado.equals("cancelados"))
            throw new IllegalArgumentException("Error el valor del parametro estado no es válido.");

        size = (size > 10) ? 10 : size;
        Pageable paginacion = PageRequest.of(page, size);
        Page<Pedido> pedidos = pedidoService.listar(estado, paginacion);
        Page<PedidoRespuestaDTO> pedidosDTO = pedidos.map(PedidoRespuestaDTO::new);

        return ResponseEntity.ok().body(new ResponseWrapper<Page<PedidoRespuestaDTO>>(
            HttpStatus.OK, 
            "Listado de pedidos éxitoso", 
            null, 
            pedidosDTO));
    }

    @PatchMapping("/pedido/{id}/confirmar")
    @Operation(summary = "Confirmación del Pedido")
    public ResponseEntity<Void> confirmarPedido(@PathVariable Long id){
        pedidoService.confirmar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("pedido/{id}/vender")
    @Operation(summary = "Vender el Pedido")
    public ResponseEntity<Void> venderPedido(@PathVariable Long id){
        pedidoService.vender(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/pedido/{id}")
    @Operation(summary = "Actualizar Pedido")
    public ResponseEntity<ResponseWrapper<PedidoRespuestaDTO>> actualizarPedido(
        @PathVariable Long id,
        @RequestBody PedidoActualizarDTO datos
    ){
        Pedido pedido = pedidoService.actualizar(id, datos);

        return ResponseEntity.ok().body(new ResponseWrapper<PedidoRespuestaDTO>(
            HttpStatus.OK, 
            "Se actualizó el pedido exitosamente.", 
            null, 
            new PedidoRespuestaDTO(pedido)));
    }
}
