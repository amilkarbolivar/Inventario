package com.inventario.repository;

import com.inventario.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto,Long> {

    List<Producto> findBySupermercadoIdAndActivoTrue(Long supermercadoId);

    List<Producto> findBySupermercadoIdAndMarcaId(Long supermercadoId , Long marcaId);

    List<Producto> findBySupermercadoIdAndProvedorIdAndActivoTrue(Long supermercadoId, Long provedorId);

}
