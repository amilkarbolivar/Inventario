package com.inventario.service;

import com.inventario.dto.categoria.CategoriaCreateDTO;
import com.inventario.dto.categoria.CategoriaDTO;
import com.inventario.dto.categoria.CategoriaUpdateDTO;
import com.inventario.exception.*;
import com.inventario.mapper.CategoriaMapper;
import com.inventario.model.Categoria;
import com.inventario.model.Supermercado;
import com.inventario.repository.CategoriaRepository;
import com.inventario.repository.SupermercadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final SupermercadoRepository supermercadoRepository;
    private final CategoriaMapper mapper;

    public CategoriaDTO crear(CategoriaCreateDTO dto) {
        // Verificar que el supermercado existe
        Supermercado supermercado = supermercadoRepository.findById(dto.getSupermercadoId())
                .orElseThrow(() -> new SupermercadoNotFoundExepcion("Supermercado no encontrado"));

        // Verificar que no exista una categoría con el mismo nombre en el supermercado
        if (categoriaRepository.existsByNombreAndSupermercadoId(dto.getNombre(), dto.getSupermercadoId())) {
            throw new CategoriaAlreadyExistsException(
                    "Ya existe una categoría con el nombre '" + dto.getNombre() + "' en este supermercado");
        }

        Categoria categoria = mapper.toEntity(dto);
        categoria.setSupermercado(supermercado);

        Categoria savedCategoria = categoriaRepository.save(categoria);
        return mapper.toDTO(savedCategoria);
    }

    @Transactional(readOnly = true)
    public CategoriaDTO obtenerPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNotFoundException("Categoría no encontrada"));
        return mapper.toDTO(categoria);
    }

    @Transactional(readOnly = true)
    public List<CategoriaDTO> obtenerPorSupermercado(Long supermercadoId) {
        // Verificar que el supermercado existe
        if (!supermercadoRepository.existsById(supermercadoId)) {
            throw new SupermercadoNotFoundExepcion("Supermercado no encontrado");
        }

        return categoriaRepository.findBySupermercadoId(supermercadoId)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoriaDTO obtenerPorNombreYSupermercado(String nombre, Long supermercadoId) {
        Categoria categoria = categoriaRepository.findByNombreAndSupermercadoId(nombre, supermercadoId)
                .orElseThrow(() -> new CategoriaNotFoundException(
                        "Categoría '" + nombre + "' no encontrada en este supermercado"));
        return mapper.toDTO(categoria);
    }

    public CategoriaDTO actualizar(Long id, CategoriaUpdateDTO dto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNotFoundException("Categoría no encontrada"));

        // Si se está actualizando el nombre, verificar que no exista otro con el mismo nombre
        if (dto.getNombre() != null && !dto.getNombre().equals(categoria.getNombre())) {
            if (categoriaRepository.existsByNombreAndSupermercadoId(
                    dto.getNombre(), categoria.getSupermercado().getId())) {
                throw new CategoriaAlreadyExistsException(
                        "Ya existe una categoría con el nombre '" + dto.getNombre() + "' en este supermercado");
            }
        }

        mapper.updateEntity(categoria, dto);
        Categoria updatedCategoria = categoriaRepository.save(categoria);
        return mapper.toDTO(updatedCategoria);
    }

    public void eliminar(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new CategoriaNotFoundException("Categoría no encontrada");
        }
        categoriaRepository.deleteById(id);
    }
}