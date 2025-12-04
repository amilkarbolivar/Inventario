package com.inventario.controllers;
import com.inventario.dto.administrador.AdministradorCreateDTO;
import com.inventario.dto.administrador.AdministradorDTO;
import com.inventario.dto.administrador.AdministradorUpdateDTO;
import com.inventario.service.AdministradorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
@RestController
@RequestMapping("/api/administradores")
@RequiredArgsConstructor
public class AdministradorController {

    private final AdministradorService administradorService;

    @PostMapping
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<AdministradorDTO> crear(@Valid @RequestBody AdministradorCreateDTO dto) {
        AdministradorDTO administrador = administradorService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(administrador);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<AdministradorDTO> obtenerPorId(@PathVariable Long id) {
        AdministradorDTO administrador = administradorService.obtenerPorId(id);
        return ResponseEntity.ok(administrador);
    }

    @GetMapping("/supermercado/{supermercadoId}")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<List<AdministradorDTO>>
    obtenerPorSupermercado(@PathVariable Long supermercadoId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Usuario autenticado: " + auth.getName());
        System.out.println("Authorities: " + auth.getAuthorities());

        List<AdministradorDTO> administradores = administradorService.obtenerPorSupermercado(supermercadoId);
        return ResponseEntity.ok(administradores);
    }

    @GetMapping("/supermercado/{supermercadoId}/activos")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<List<AdministradorDTO>>
    obtenerActivosPorSupermercado(@PathVariable Long supermercadoId) {
        List<AdministradorDTO> administradores =
                administradorService.obtenerActivosPorSupermercado(supermercadoId);
        return ResponseEntity.ok(administradores);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<AdministradorDTO> actualizar(@PathVariable Long id, @Valid @RequestBody AdministradorUpdateDTO dto) {
        AdministradorDTO administrador = administradorService.actualizar(id, dto);
        return ResponseEntity.ok(administrador);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        administradorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/desactivar")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<AdministradorDTO> desactivar(@PathVariable Long id) {
        AdministradorDTO administrador = administradorService.desactivar(id);
        return ResponseEntity.ok(administrador);
    }

    @PutMapping("/{id}/activar")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<AdministradorDTO> activar(@PathVariable Long id) {
        AdministradorDTO administrador = administradorService.activar(id);
        return ResponseEntity.ok(administrador);
    }
}