package com.ecommerce.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.entity.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long>{

    boolean existsByNombre(String nombre);

    Page<Producto> findByEstadoTrue(Pageable paginacion);
    
}
