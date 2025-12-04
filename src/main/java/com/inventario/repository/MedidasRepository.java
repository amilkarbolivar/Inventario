package com.inventario.repository;

import com.inventario.model.Marca;
import com.inventario.model.Medida;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedidasRepository extends JpaRepository<Medida,Long> {

    List<Medida> findBySupermercadoId(Long supermercadoId);
}
