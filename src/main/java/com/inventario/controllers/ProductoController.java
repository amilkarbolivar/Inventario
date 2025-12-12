package com.inventario.controllers;

import com.inventario.dto.producto.ProductoCreateDTO;
import com.inventario.dto.producto.ProductoDTO;
import com.inventario.dto.producto.ProductoUpdateDTO;
import com.inventario.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/producto")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<ProductoDTO> crear(@Valid @RequestBody ProductoCreateDTO dto) {
        ProductoDTO producto = productoService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(producto);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<ProductoDTO> obtenerPorId(@PathVariable Long id) {
        ProductoDTO producto = productoService.obtenerPorId(id);
        return ResponseEntity.ok(producto);
    }

    @GetMapping("/supermercado/{supermercadoId}/activos")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<List<ProductoDTO>> obtenerActivosPorSupermercado(@PathVariable Long supermercadoId) {
        List<ProductoDTO> productos = productoService.obtenerActivosPorSupermercado(supermercadoId);
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/supermercado/{supermercadoId}/marca/{marcaId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<List<ProductoDTO>> obtenerPorMarca(
            @PathVariable Long supermercadoId,
            @PathVariable Long marcaId) {
        List<ProductoDTO> productos = productoService.obtenerPorMarca(supermercadoId, marcaId);
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/supermercado/{supermercadoId}/proveedor/{provedorId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<List<ProductoDTO>> obtenerPorProveedor(
            @PathVariable Long supermercadoId,
            @PathVariable Long provedorId) {
        List<ProductoDTO> productos = productoService.obtenerPorProveedor(supermercadoId, provedorId);
        return ResponseEntity.ok(productos);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<ProductoDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProductoUpdateDTO dto) {
        ProductoDTO producto = productoService.actualizar(id, dto);
        return ResponseEntity.ok(producto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/desactivar")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<ProductoDTO> desactivar(@PathVariable Long id) {
        ProductoDTO producto = productoService.desactivar(id);
        return ResponseEntity.ok(producto);
    }

    @PutMapping("/{id}/activar")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<ProductoDTO> activar(@PathVariable Long id) {
        ProductoDTO producto = productoService.activar(id);
        return ResponseEntity.ok(producto);
    }

    @PutMapping("/{id}/stock")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<ProductoDTO> actualizarStock(
            @PathVariable Long id,
            @RequestParam Integer cantidad) {
        ProductoDTO producto = productoService.actualizarStock(id, cantidad);
        return ResponseEntity.ok(producto);
    }
}