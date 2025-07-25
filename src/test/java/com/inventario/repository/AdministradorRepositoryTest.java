package com.inventario.repository;

import com.inventario.model.Administrador;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AdministradorRepositoryTest {

    @Autowired
    private AdministradorRepository administradorRepository;

    private final Long supermercadoId = 1L;
    private final String rolSuperadmin = "SUPERADMIN";
    private final String rolAdmin = "ADMIN";

    @Test
    void testFindBySupermercadoId() {
        List<Administrador> admins = administradorRepository.findBySupermercadoId(supermercadoId);
        assertNotNull(admins);
        assertFalse(admins.isEmpty(), "Debe haber administradores en el supermercado con ID 1");
    }

    @Test
    void testFindBySupermercadoIdAndActivoTrue() {
        List<Administrador> activos = administradorRepository.findBySupermercadoIdAndActivoTrue(supermercadoId);
        assertNotNull(activos);
        assertTrue(activos.stream().allMatch(Administrador::getActivo), "Todos deben estar activos");
    }

    @Test
    void testFindBySupermercadoIdAndActivoFalse() {
        List<Administrador> inactivos = administradorRepository.findBySupermercadoIdAndActivoFalse(supermercadoId);
        assertNotNull(inactivos);
        assertTrue(inactivos.stream().noneMatch(Administrador::getActivo), "Todos deben estar inactivos");
    }

    @Test
    void testFindBySupermercadoIdAndRolNot() {
        List<Administrador> noSuperadmins = administradorRepository.findBySupermercadoIdAndRolNot(supermercadoId, rolSuperadmin);
        assertNotNull(noSuperadmins);
        assertTrue(noSuperadmins.stream().noneMatch(a -> rolSuperadmin.equals(a.getRol())), "Ninguno debe ser SUPERADMIN");
    }

    @Test
    void testFindByCorreoAndSupermercadoId() {
        String correo = "superadmin@central.com"; // usa un correo real en tu data.sql o base
        Optional<Administrador> admin = administradorRepository.findByCorreoAndSupermercadoId(correo, supermercadoId);
        assertTrue(admin.isPresent(), "Debe encontrar un admin con ese correo y supermercado");
    }

    @Test
    void testFindBySupermercadoIdAndRol() {
        Optional<Administrador> superadmin = administradorRepository.findBySupermercadoIdAndRol(supermercadoId, rolSuperadmin);
        assertTrue(superadmin.isPresent(), "Debe encontrar un superadmin en ese supermercado");
    }

    @Test
    void testFindByIdAndRol() {
        Long id = 1L; // Asegúrate de tener un admin con este ID y rol esperado
        Optional<Administrador> admin = administradorRepository.findByIdAndRol(id, rolSuperadmin);
        assertTrue(admin.isPresent(), "Debe encontrar un admin con ID 1 y rol SUPERADMIN");
    }
}
