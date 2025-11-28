package com.inventario.service;

import com.inventario.dto.cliente.ClienteDTO;
import com.inventario.dto.cliente.CreateClienteDto;
import com.inventario.exception.AdministradorNotFoundException;
import com.inventario.exception.ClienteNotFoundException;
import com.inventario.exception.SupermercadoNotFoundExepcion;
import com.inventario.mapper.ClienteMapper;
import com.inventario.model.Cliente;
import com.inventario.model.Supermercado;
import com.inventario.repository.ClienteRepository;
import com.inventario.repository.SupermercadoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClienteService {

    private final SupermercadoRepository supermercadoRepository;
    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    public ClienteService(SupermercadoRepository supermercadoRepository, ClienteRepository clienteRepository, ClienteMapper clienteMapper) {
        this.supermercadoRepository = supermercadoRepository;
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
    }

    public ClienteDTO crear(CreateClienteDto dto) {
        // 1) Validar que exista el supermercado
        Supermercado supermercado = supermercadoRepository.findById(dto.getSupermercadoId())
                .orElseThrow(() -> new AdministradorNotFoundException(
                        "Supermercado con id " + dto.getSupermercadoId() + " no encontrado"));

        // 2) Comprobar si ya existe un cliente con la misma cédula en ese supermercado
        Optional<Cliente> existente = clienteRepository.findByCedulaAndSupermercadoId(dto.getCedula(), dto.getSupermercadoId());
        if (existente.isPresent()) {
            throw new RuntimeException("Cliente con cédula " + dto.getCedula() + " ya existe en el supermercado");
        }

        // 3) Mapear DTO -> entidad
        Cliente cliente = clienteMapper.toEntity(dto,supermercado);


        // 4) Guardar
        Cliente guardado = clienteRepository.save(cliente);

        // 5) Mapear entidad guardada -> DTO de respuesta
        return clienteMapper.toDto(guardado);
    }
    public ClienteDTO buscar (Long id) {
        Cliente cliente =clienteRepository.findById(id)
                .orElseThrow(() -> new AdministradorNotFoundException("Administrador no encontrado"));

        return clienteMapper.toDto(cliente);
    }

    public List<ClienteDTO> todos_clientes(Long id) {
        // 1️⃣ Verificar que el supermercado existe
        Supermercado supermercado = supermercadoRepository.findById(id)
                .orElseThrow(() -> new AdministradorNotFoundException("Supermercado no encontrado"));

        // 2️⃣ Buscar todos los clientes de ese supermercado
        List<Cliente> clientes = clienteRepository.findBySupermercadoId(id);

        // 3️⃣ Convertir entidades a DTOs
        return clientes.stream()
                .map(clienteMapper::toDto)
                .collect(Collectors.toList());
    }

    public void eliminar (Long ClienteId, Long SupermercadoId){
        Supermercado supermercado =supermercadoRepository.findById(SupermercadoId)
                .orElseThrow(()-> new SupermercadoNotFoundExepcion("supermercado no existe"));
        Cliente cliente =clienteRepository.findById(ClienteId)
                .orElseThrow(()-> new ClienteNotFoundException("cliente no encontrado"));
        if(cliente.getSupermercado() != null && cliente.getSupermercado().getId().equals(supermercado.getId())){
            clienteRepository.delete(cliente);
        }else{
            throw new RuntimeException("El cliente no pertenece al supermercado indicado");
        }

    }
}
