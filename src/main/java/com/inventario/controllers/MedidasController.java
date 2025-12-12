package com.inventario.controllers;

import com.inventario.model.Medida;
import com.inventario.service.MedidaService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/medidas")
public class MedidasController {

    private final MedidaService medidaService;

    public MedidasController(MedidaService medidaService) {
        this.medidaService = medidaService;
    }

    @PostMapping
    public Medida crear(@RequestBody Medida medida) {
        return medidaService.crear(medida);
    }

    @GetMapping
    public List<Medida> listar() {
        return medidaService.listar();
    }

    @GetMapping("/{id}")
    public Medida buscar(@PathVariable Long id) {
        return medidaService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Medida actualizar(@PathVariable Long id, @RequestBody Medida medida) {
        return medidaService.actualizar(id, medida);
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Long id) {
        medidaService.eliminar(id);
        return "Medida eliminada";
    }
}
