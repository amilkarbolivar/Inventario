package com.inventario.repository;

import com.inventario.model.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Long> {

    List<Administrador> findBySupermercadoId(Long supermercadoId);

    List<Administrador> findBySupermercadoIdAndActivoTrue(Long supermercadoId);

    List<Administrador> findBySupermercadoIdAndActivoFalse(Long supermercadoId);

    List<Administrador> findBySupermercadoIdAndRolNot(Long supermercadoId, String rol);

    Optional<Administrador> findByCorreoAndSupermercadoId(String correo, Long supermercadoId);

    Optional<Administrador> findBySupermercadoIdAndRol(Long supermercadoId, String rol);

    Optional<Administrador> findByIdAndRol(Long id, String rol);

    Optional<Administrador> findByCorreo(String correo);

    Optional<Administrador> findByCorreoAndPassword(String correo ,String password);
}