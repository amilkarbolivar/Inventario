package com.inventario.service;
import com.inventario.dto.administrador.AdministradorCreateDTO;
import com.inventario.dto.administrador.AdministradorDTO;
import com.inventario.dto.administrador.AdministradorUpdateDTO;
import com.inventario.exception.*;
import com.inventario.mapper.AdministradorMapper;
import com.inventario.model.Administrador;
import com.inventario.model.Supermercado;
import com.inventario.repository.AdministradorRepository;
import com.inventario.repository.SupermercadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
@Transactional
public class AdministradorService {

    private final AdministradorRepository administradorRepository;
    private final SupermercadoRepository supermercadoRepository;
    private final AdministradorMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public AdministradorDTO crear(AdministradorCreateDTO dto) {
        // Verificar si el supermercado existe
        Supermercado supermercado = supermercadoRepository.findById(dto.getSupermercadoId())
                        .orElseThrow(() -> new AdministradorNotFoundException("Supermercado no encontrado"));

                                // Verificar si ya existe un administrador con ese correo en el supermercado
        if (administradorRepository.findByCorreoAndSupermercadoId(dto.getCorreo(),
                dto.getSupermercadoId()).isPresent()) {
            throw new AdministradorAlreadyExistsException("Ya existe un administrador con ese correo en este supermercado");
        }

        // Crear el administrador
        Administrador administrador = mapper.toEntity(dto);
        administrador.setPassword(passwordEncoder.encode(dto.getPassword()));
        administrador.setSupermercado(supermercado);

        Administrador savedAdmin = administradorRepository.save(administrador);
        return mapper.toDTO(savedAdmin);
    }

    @Transactional(readOnly = true)
    public AdministradorDTO obtenerPorId(Long id) {
        Administrador administrador = administradorRepository.findById(id)
                .orElseThrow(() -> new AdministradorNotFoundException("Administrador no encontrado"));
        return mapper.toDTO(administrador);
    }

    @Transactional(readOnly = true)
    public List<AdministradorDTO> obtenerPorSupermercado(Long supermercadoId) {
        return administradorRepository.findBySupermercadoId(supermercadoId)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AdministradorDTO> obtenerActivosPorSupermercado(Long supermercadoId) {
        return
                administradorRepository.findBySupermercadoIdAndActivoTrue(supermercadoId)
                        .stream()
                        .map(mapper::toDTO)
                        .collect(Collectors.toList());
    }

    public AdministradorDTO actualizar(Long id, AdministradorUpdateDTO dto) {
        Administrador administrador = administradorRepository.findById(id)
                .orElseThrow(() -> new AdministradorNotFoundException("Administrador no encontrado"));

                        // Si se está actualizando el correo, verificar que no exista otro con el mismo correo
        if (dto.getCorreo() != null && !dto.getCorreo().equals(administrador.getCorreo())) {
            if (administradorRepository.findByCorreoAndSupermercadoId(dto.getCorreo(),
                    administrador.getSupermercado().getId()).isPresent()) {
                throw new AdministradorAlreadyExistsException("Ya existe un administrador con ese correo en este supermercado");
            }
        }

        // Si se está actualizando la contraseña, encriptarla
        if (dto.getPassword() != null) {
            dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        mapper.updateEntity(administrador, dto);
        Administrador updatedAdmin = administradorRepository.save(administrador);
        return mapper.toDTO(updatedAdmin);
    }

    public void eliminar(Long id) {
        if (!administradorRepository.existsById(id)) {
            throw new AdministradorNotFoundException("Administrador no encontrado");
        }
        administradorRepository.deleteById(id);
    }

    public AdministradorDTO desactivar(Long id) {
        Administrador administrador = administradorRepository.findById(id)
                .orElseThrow(() -> new AdministradorNotFoundException("Administrador no encontrado"));

                        administrador.setActivo(false);
        Administrador updatedAdmin = administradorRepository.save(administrador);
        return mapper.toDTO(updatedAdmin);
    }

    public AdministradorDTO activar(Long id) {
        Administrador administrador = administradorRepository.findById(id)
                .orElseThrow(() -> new AdministradorNotFoundException("Administrador no encontrado"));

                        administrador.setActivo(true);
        Administrador updatedAdmin = administradorRepository.save(administrador);
        return mapper.toDTO(updatedAdmin);
    }

    @Transactional(readOnly = true)
    public AdministradorDTO obtenerPorCorreoYSupermercado(String correo, Long supermercadoId) {
        Administrador administrador =
                administradorRepository.findByCorreoAndSupermercadoId(correo, supermercadoId)
                        .orElseThrow(() -> new AdministradorNotFoundException("Administrador no encontrado"));
        return mapper.toDTO(administrador);
    }
}