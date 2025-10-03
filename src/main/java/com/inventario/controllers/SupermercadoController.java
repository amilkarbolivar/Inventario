package com.inventario.controllers;

import com.inventario.dto.CreateSupermercadoDTO;
import com.inventario.dto.SupermercadoDTO;
import com.inventario.service.SupermercadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/supermercado")
@RequiredArgsConstructor
public class SupermercadoController {

    private final SupermercadoService supermercadoService;

    @PostMapping("/registro")
    public ResponseEntity<SupermercadoDTO> crear(@Valid @RequestBody CreateSupermercadoDTO dto) {
        SupermercadoDTO supermercadoDTO = supermercadoService.crear(dto);
        return ResponseEntity.ok(supermercadoDTO);
    }
}

