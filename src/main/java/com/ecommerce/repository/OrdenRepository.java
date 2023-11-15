package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.entity.Orden;

public interface OrdenRepository extends JpaRepository<Orden, Long>{
    
}
