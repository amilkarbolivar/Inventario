package com.inventario.service;

import com.inventario.dto.proveedor.ProveedorCreateDTO;
import com.inventario.dto.proveedor.ProveedorDTO;
import com.inventario.dto.proveedor.ProveedorUpdateDTO;
import com.inventario.exception.ProveedorAlreadyExistsException;
import com.inventario.exception.ProveedorNotFoundException;
import com.inventario.exception.SupermercadoNotFoundExepcion;
import com.inventario.mapper.ProveedorMapper;
import com.inventario.model.Provedor;
import com.inventario.model.Supermercado;
import com.inventario.repository.ProvedorRepositorio;
import com.inventario.repository.SupermercadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProveedorService {

    private final ProvedorRepositorio proveedorRepository;
    private final SupermercadoRepository supermercadoRepository;
    private final ProveedorMapper mapper;

    public ProveedorDTO crear(ProveedorCreateDTO dto) {
        // Verificar que el supermercado existe
        Supermercado supermercado = supermercadoRepository.findById(dto.getSupermercadoId())
                .orElseThrow(() -> new SupermercadoNotFoundExepcion(
                        "Supermercado con id " + dto.getSupermercadoId() + " no encontrado"));

        // Verificar que no exista un proveedor con el mismo correo en el supermercado
        if (proveedorRepository.existsByCorreoAndSupermercadoId(dto.getCorreo(), dto.getSupermercadoId())) {
            throw new ProveedorAlreadyExistsException(
                    "Ya existe un proveedor con el correo " + dto.getCorreo() + " en este supermercado");
        }

        // Crear el proveedor
        Provedor proveedor = mapper.toEntity(dto, supermercado);

        // Guardar
        Provedor guardado = proveedorRepository.save(proveedor);

        return mapper.toDTO(guardado);
    }

    @Transactional(readOnly = true)
    public ProveedorDTO obtenerPorId(Long id) {
        Provedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new ProveedorNotFoundException("Proveedor no encontrado"));

        return mapper.toDTO(proveedor);
    }

    @Transactional(readOnly = true)
    public List<ProveedorDTO> obtenerPorSupermercado(Long supermercadoId) {
        // Verificar que el supermercado existe
        if (!supermercadoRepository.existsById(supermercadoId)) {
            throw new SupermercadoNotFoundExepcion("Supermercado no encontrado");
        }

        List<Provedor> proveedores = proveedorRepository.findBySupermercadoId(supermercadoId);

        return proveedores.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProveedorDTO obtenerPorCorreo(String correo, Long supermercadoId) {
        // Verificar que el supermercado existe
        if (!supermercadoRepository.existsById(supermercadoId)) {
            throw new SupermercadoNotFoundExepcion("Supermercado no encontrado");
        }

        Provedor proveedor = proveedorRepository.findByCorreoAndSupermercadoId(correo, supermercadoId)
                .orElseThrow(() -> new ProveedorNotFoundException(
                        "Proveedor con correo " + correo + " no encontrado en este supermercado"));

        return mapper.toDTO(proveedor);
    }

    public ProveedorDTO actualizar(Long id, ProveedorUpdateDTO dto) {
        // Buscar el proveedor existente
        Provedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new ProveedorNotFoundException("Proveedor no encontrado"));

        // Si se va a cambiar el correo, verificar que no exista otro con el mismo correo
        if (dto.getCorreo() != null && !proveedor.getCorreo().equals(dto.getCorreo())) {
            if (proveedorRepository.existsByCorreoAndSupermercadoId(
                    dto.getCorreo(), proveedor.getSupermercado().getId())) {
                throw new ProveedorAlreadyExistsException(
                        "Ya existe un proveedor con el correo " + dto.getCorreo() + " en este supermercado");
            }
        }

        // Actualizar la entidad
        mapper.updateEntity(proveedor, dto);

        // Guardar cambios
        Provedor actualizado = proveedorRepository.save(proveedor);

        return mapper.toDTO(actualizado);
    }

    public void eliminar(Long id, Long supermercadoId) {
        // Verificar que el supermercado existe
        Supermercado supermercado = supermercadoRepository.findById(supermercadoId)
                .orElseThrow(() -> new SupermercadoNotFoundExepcion("Supermercado no encontrado"));

        // Verificar que el proveedor existe
        Provedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new ProveedorNotFoundException("Proveedor no encontrado"));

        // Verificar que el proveedor pertenece al supermercado
        if (proveedor.getSupermercado() == null ||
                !proveedor.getSupermercado().getId().equals(supermercado.getId())) {
            throw new ProveedorNotFoundException(
                    "El proveedor no pertenece al supermercado indicado");
        }

        // Eliminar el proveedor
        proveedorRepository.delete(proveedor);
    }
}