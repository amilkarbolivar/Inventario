package com.inventario.service;

import com.inventario.dto.supermercado.CreateSupermercadoDTO;
import com.inventario.dto.supermercado.SupermercadoDTO;
import com.inventario.dto.supermercado.SupermercadoUpdateDTO;
import com.inventario.exception.SupermercadoAlreadyExistsExeption;
import com.inventario.exception.SupermercadoNotFoundExepcion;
import com.inventario.mapper.SupermercadoMapper;
import com.inventario.model.Administrador;
import com.inventario.model.Supermercado;
import com.inventario.repository.AdministradorRepository;
import com.inventario.repository.SupermercadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class SupermercadoService {

    private final AdministradorRepository administradorRepository;
    private final SupermercadoRepository supermercadoRepository;
    private final SupermercadoMapper supermercadoMapper;
    private final AdministradorService administradorService;
    private final PasswordEncoder passwordEncoder;

    public SupermercadoDTO crear(CreateSupermercadoDTO dto) {
        if (supermercadoRepository.findByNombreAndCorreo(dto.getNombre(), dto.getCorreo()).isPresent()) {
            throw new SupermercadoAlreadyExistsExeption("Ya existe un supermercado");
        }
        if (administradorRepository.findByCorreo(dto.getCorreo()).isPresent()) {
            throw new SupermercadoAlreadyExistsExeption("EL CORREO YA ESTA UTILIZADO");
        }

        Supermercado supermercado = supermercadoMapper.toEntity(dto);
        Supermercado savedSupermercado = supermercadoRepository.save(supermercado);

        Administrador admin = new Administrador();
        admin.setNombre(dto.getAdministrador().getNombre());
        admin.setCorreo(dto.getAdministrador().getCorreo());
        admin.setPassword(passwordEncoder.encode(dto.getAdministrador().getPassword()));
        admin.setRol("ROLE_SUPERADMIN");
        admin.setSupermercado(savedSupermercado); // ← aquí le asignas el supermercado recién creado
        administradorRepository.save(admin);


        return supermercadoMapper.toDTO(savedSupermercado);
    }

    public SupermercadoDTO buscar(Long id) {
        Supermercado supermercado = supermercadoRepository.findById(id)
                .orElseThrow(() -> new SupermercadoNotFoundExepcion("supermercado no encontrado"));

        return supermercadoMapper.toDTO(supermercado);
    }

    public SupermercadoDTO actualizar (Long id, SupermercadoUpdateDTO dto){
        Optional<Supermercado> supermercado2 =supermercadoRepository.findById(id);
        if(supermercado2.isEmpty()){
            throw new SupermercadoNotFoundExepcion("supermercado no existe");
        }
        Supermercado supermercado = supermercado2.get();
        supermercadoMapper.updateSupermercado(supermercado,dto);
        supermercadoRepository.save(supermercado);
        return supermercadoMapper.toDTO(supermercado);
    }
}

