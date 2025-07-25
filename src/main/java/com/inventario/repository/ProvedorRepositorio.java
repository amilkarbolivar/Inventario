package com.inventario.repository;

import com.inventario.model.Provedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProvedorRepositorio extends JpaRepository<Provedor, Long> {

    // Todos los provedores de un supermercado
    List<Provedor> findBySupermercadoId(Long supermercadoId);

    // Buscar por correo exacto — puede no existir, por eso usamos Optional
    Optional<Provedor> findByCorreoAndSupermercadoId(String correo,Long supermercadoId);

    // Validar si ya existe un proveedor con ese correo en ese supermercado
    boolean existsByCorreoAndSupermercadoId(String correo, Long supermercadoId);
}

