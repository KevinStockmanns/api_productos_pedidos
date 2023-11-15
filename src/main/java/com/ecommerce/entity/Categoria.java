package com.ecommerce.entity;

import com.ecommerce.dto.categoria.CategoriaActualizarDTO;
import com.ecommerce.dto.categoria.CategoriaRegistroDTO;
import com.ecommerce.utils.Utils;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categorias")
@Data @NoArgsConstructor @AllArgsConstructor
public class Categoria {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 150)
    private String nombre;

    public Categoria(CategoriaRegistroDTO datos) {
        this.nombre = Utils.formatTitle(datos.nombre());
    }

    public Categoria(CategoriaActualizarDTO datos) {
        this.nombre = Utils.formatTitle(datos.nombre());
    }
}
