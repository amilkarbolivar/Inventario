package com.inventario.service;

import com.inventario.model.Medida;
import com.inventario.repository.MedidasRepository;
import com.inventario.repository.MedidasRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MedidaService {

    private final MedidasRepository medidaRepository;

    public MedidaService(MedidasRepository medidaRepository) {
        this.medidaRepository = medidaRepository;
    }

    public Medida crear(Medida medida) {
        return medidaRepository.save(medida);
    }

    public List<Medida> listar() {
        return medidaRepository.findAll();
    }

    public Medida buscarPorId(Long id) {
        return medidaRepository.findById(id).orElse(null);
    }

    public Medida actualizar(Long id, Medida nueva) {
        Medida m = buscarPorId(id);
        if (m == null) return null;

        m.setUnidad(nueva.getUnidad());
        return medidaRepository.save(m);
    }

    public void eliminar(Long id) {
        medidaRepository.deleteById(id);
    }
}
