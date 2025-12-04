package com.inventario.controllers;

import com.inventario.dto.categoria.CategoriaCreateDTO;
import com.inventario.dto.categoria.CategoriaDTO;
import com.inventario.dto.categoria.CategoriaUpdateDTO;
import com.inventario.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<CategoriaDTO> crear(@Valid @RequestBody CategoriaCreateDTO dto) {
        CategoriaDTO categoria = categoriaService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoria);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<CategoriaDTO> obtenerPorId(@PathVariable Long id) {
        CategoriaDTO categoria = categoriaService.obtenerPorId(id);
        return ResponseEntity.ok(categoria);
    }

    @GetMapping("/supermercado/{supermercadoId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<List<CategoriaDTO>> obtenerPorSupermercado(@PathVariable Long supermercadoId) {
        List<CategoriaDTO> categorias = categoriaService.obtenerPorSupermercado(supermercadoId);
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/supermercado/{supermercadoId}/nombre/{nombre}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<CategoriaDTO> obtenerPorNombreYSupermercado(
            @PathVariable Long supermercadoId,
            @PathVariable String nombre) {
        CategoriaDTO categoria = categoriaService.obtenerPorNombreYSupermercado(nombre, supermercadoId);
        return ResponseEntity.ok(categoria);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<CategoriaDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody CategoriaUpdateDTO dto) {
        CategoriaDTO categoria = categoriaService.actualizar(id, dto);
        return ResponseEntity.ok(categoria);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        categoriaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}