package com.ecommerce.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.entity.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{

    Page<Pedido> findByPendienteTrue(Pageable paginacion);

    Page<Pedido> findByConfirmadoTrue(Pageable paginacion);

    Page<Pedido> findByPendienteFalseAndConfirmadoFalseAndVendidoFalse(Pageable paginacion);

    Page<Pedido> findByVendidoTrue(Pageable paginacion);
    
}
