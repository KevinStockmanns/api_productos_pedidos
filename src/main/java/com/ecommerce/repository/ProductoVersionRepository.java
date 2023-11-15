package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.entity.ProductoVersion;

public interface ProductoVersionRepository extends JpaRepository<ProductoVersion, Long> {
    
}
