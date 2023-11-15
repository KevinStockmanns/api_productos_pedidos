package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.entity.HistorialPrecios;

import jakarta.transaction.Transactional;

public interface HistorialPrecioRepository extends JpaRepository<HistorialPrecios, Long>{

    @Modifying
    @Transactional
    @Query("DELETE FROM HistorialPrecios h WHERE h.producto.id = :id")
    void deleteAllByIdProducto(@Param("id") Long id);
    
}
