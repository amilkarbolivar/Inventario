package com.inventario.controllers;

import com.inventario.dto.supermercado.CreateSupermercadoDTO;
import com.inventario.dto.supermercado.SupermercadoDTO;
import com.inventario.dto.supermercado.SupermercadoUpdateDTO;
import com.inventario.service.SupermercadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/buscar/{id}")
    public ResponseEntity<SupermercadoDTO> buscar (@PathVariable Long id){
        SupermercadoDTO supermercadoDTO= supermercadoService.buscar(id);
        return ResponseEntity.ok(supermercadoDTO);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<SupermercadoDTO> actualizar(@Valid @RequestBody SupermercadoUpdateDTO dto ,@PathVariable Long id){
        SupermercadoDTO supermercadoDTO =supermercadoService.actualizar(id, dto);
        return ResponseEntity.ok(supermercadoDTO);
    }
}

