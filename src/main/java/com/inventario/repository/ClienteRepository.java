package com.inventario.repository;

import com.inventario.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {

    List<Cliente> findBySupermercadoId(Long supermercadoId);

    Optional<Cliente> findByCedulaAndSupermercadoId(String cedula, Long supermercadoId);

}
