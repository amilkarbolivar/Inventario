package com.inventario.controllers;

import com.inventario.model.Marca;
import com.inventario.service.MarcaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/marcas")
@RequiredArgsConstructor
public class MarcaController {

    private final MarcaService marcaService;

    @GetMapping("/{id}")
    public List<Marca> obtenerPorSupermercado(@PathVariable Long id) {
        return marcaService.getMarcasBySupermercado(id);
    }

    @PostMapping
    public Marca crear(@RequestBody Marca marca) {
        return marcaService.crear(marca);
    }

    @PutMapping("/{id}")
    public Marca actualizar(@PathVariable Long id, @RequestBody Marca marca) {
        return marcaService.actualizar(id, marca);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        marcaService.eliminar(id);
    }
}
