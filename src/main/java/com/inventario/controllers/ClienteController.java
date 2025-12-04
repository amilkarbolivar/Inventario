package com.inventario.controllers;
import com.inventario.dto.cliente.ClienteDTO;
import com.inventario.dto.cliente.CreateClienteDto;
import com.inventario.dto.cliente.UpdateClienteDto;
import com.inventario.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cliente")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<ClienteDTO> crear(@Valid @RequestBody CreateClienteDto dto) {
        ClienteDTO cliente = clienteService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<ClienteDTO> buscar(@PathVariable Long id) {
        ClienteDTO cliente = clienteService.buscar(id);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping("/supermercado/{supermercadoId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<List<ClienteDTO>> obtenerPorSupermercado(@PathVariable Long supermercadoId) {
        List<ClienteDTO> clientes = clienteService.todos_clientes(supermercadoId);
        return ResponseEntity.ok(clientes);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<ClienteDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody UpdateClienteDto dto) {
        ClienteDTO cliente = clienteService.actualizar(id, dto);
        return ResponseEntity.ok(cliente);
    }

    @DeleteMapping("/{id}/supermercado/{supermercadoId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id,
            @PathVariable Long supermercadoId) {
        clienteService.eliminar(id, supermercadoId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/supermercado/{supermercadoId}/cedula/{cedula}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<ClienteDTO> buscarPorCedula(
            @PathVariable Long supermercadoId,
            @PathVariable String cedula) {
        ClienteDTO cliente = clienteService.buscarPorCedula(cedula, supermercadoId);
        return ResponseEntity.ok(cliente);
    }
}
