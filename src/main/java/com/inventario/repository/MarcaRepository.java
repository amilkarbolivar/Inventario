package com.inventario.repository;

import com.inventario.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarcaRepository extends JpaRepository<Marca,Long> {

    List<Marca> findBySupermercadoId(Long supermercadoId);
}
