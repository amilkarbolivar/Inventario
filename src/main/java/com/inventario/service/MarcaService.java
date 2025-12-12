package com.inventario.service;

import com.inventario.model.Marca;
import com.inventario.model.Supermercado;
import com.inventario.repository.MarcaRepository;
import com.inventario.repository.SupermercadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarcaService {

    private final MarcaRepository marcaRepository;
    private final SupermercadoRepository supermercadoRepository;

    public List<Marca> getMarcasBySupermercado(Long supermercadoId) {
        return marcaRepository.findBySupermercadoId(supermercadoId);
    }

    public Marca crear(Marca marca) {
        if (marca.getSupermercado() != null) {
            Long id = marca.getSupermercado().getId();
            Supermercado s = supermercadoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Supermercado no encontrado"));
            marca.setSupermercado(null);
        }
        return marcaRepository.save(marca);
    }

    public Marca actualizar(Long id, Marca nueva) {
        Marca marca = marcaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Marca no encontrada"));

        marca.setNombre(nueva.getNombre());
        return marcaRepository.save(marca);
    }

    public void eliminar(Long id) {
        marcaRepository.deleteById(id);
    }
}
