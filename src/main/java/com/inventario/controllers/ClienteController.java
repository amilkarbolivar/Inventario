package com.inventario.controllers;

import com.inventario.dto.cliente.ClienteDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cliente")
@AllArgsConstructor
public class ClienteController {
    public ResponseEntity<ClienteDTO> buscar (@PathVariable Long id){
        
    }
}
