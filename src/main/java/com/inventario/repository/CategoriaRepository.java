package com.inventario.repository;

import com.inventario.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria,Long> {

    List<Categoria> findBySupermercadoId(Long supermercadoId);

    Optional<Categoria> findByNombreAndSupermercadoId(String nombre, Long supermercadoId);

    boolean existsByNombreAndSupermercadoId(String nombre, Long supermercadoId);

}
