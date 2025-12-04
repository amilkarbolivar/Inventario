package com.inventario.service;
import com.inventario.dto.cliente.ClienteDTO;
import com.inventario.dto.cliente.CreateClienteDto;
import com.inventario.dto.cliente.UpdateClienteDto;
import com.inventario.exception.ClienteAlreadyExistException;
import com.inventario.exception.ClienteNotFoundException;
import com.inventario.exception.SupermercadoNotFoundExepcion;
import com.inventario.mapper.ClienteMapper;
import com.inventario.model.Cliente;
import com.inventario.model.Supermercado;
import com.inventario.repository.ClienteRepository;
import com.inventario.repository.SupermercadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ClienteService {

    private final SupermercadoRepository supermercadoRepository;
    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    public ClienteDTO crear(CreateClienteDto dto) {
        // Validar que exista el supermercado
        Supermercado supermercado = supermercadoRepository.findById(dto.getSupermercadoId())
                .orElseThrow(() -> new SupermercadoNotFoundExepcion(
                        "Supermercado con id " + dto.getSupermercadoId() + " no encontrado"));

        // Comprobar si ya existe un cliente con la misma cédula en ese supermercado
        Optional<Cliente> existente = clienteRepository
                .findByCedulaAndSupermercadoId(dto.getCedula(), dto.getSupermercadoId());

        if (existente.isPresent()) {
            throw new ClienteAlreadyExistException(
                    "Cliente con cédula " + dto.getCedula() + " ya existe en el supermercado");
        }

        // Mapear DTO -> entidad
        Cliente cliente = clienteMapper.toEntity(dto, supermercado);

        // Guardar
        Cliente guardado = clienteRepository.save(cliente);

        // Mapear entidad guardada -> DTO de respuesta
        return clienteMapper.toDto(guardado);
    }

    @Transactional(readOnly = true)
    public ClienteDTO buscar(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado"));

        return clienteMapper.toDto(cliente);
    }

    @Transactional(readOnly = true)
    public List<ClienteDTO> todos_clientes(Long supermercadoId) {
        // Verificar que el supermercado existe
        if (!supermercadoRepository.existsById(supermercadoId)) {
            throw new SupermercadoNotFoundExepcion("Supermercado no encontrado");
        }

        // Buscar todos los clientes de ese supermercado
        List<Cliente> clientes = clienteRepository.findBySupermercadoId(supermercadoId);

        // Convertir entidades a DTOs
        return clientes.stream()
                .map(clienteMapper::toDto)
                .collect(Collectors.toList());
    }

    public ClienteDTO actualizar(Long id, UpdateClienteDto dto) {
        // Buscar el cliente existente
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado"));

        // Si se va a cambiar la cédula, verificar que no exista otra igual
        if (!cliente.getCedula().equals(dto.getCedula())) {
            Optional<Cliente> existente = clienteRepository
                    .findByCedulaAndSupermercadoId(dto.getCedula(), cliente.getSupermercado().getId());

            if (existente.isPresent()) {
                throw new ClienteAlreadyExistException(
                        "Ya existe un cliente con cédula " + dto.getCedula() + " en este supermercado");
            }
        }

        // Si se proporciona un nuevo supermercadoId, validarlo
        Supermercado nuevoSupermercado = null;
        if (dto.getSupermercadoId() != null) {
            nuevoSupermercado = supermercadoRepository.findById(dto.getSupermercadoId())
                    .orElseThrow(() -> new SupermercadoNotFoundExepcion(
                            "Supermercado con id " + dto.getSupermercadoId() + " no encontrado"));
        }

        // Actualizar la entidad
        clienteMapper.updateEntity(dto, cliente, nuevoSupermercado);

        // Guardar cambios
        Cliente actualizado = clienteRepository.save(cliente);

        return clienteMapper.toDto(actualizado);
    }

    public void eliminar(Long clienteId, Long supermercadoId) {
        // Verificar que el supermercado existe
        Supermercado supermercado = supermercadoRepository.findById(supermercadoId)
                .orElseThrow(() -> new SupermercadoNotFoundExepcion("Supermercado no existe"));

        // Verificar que el cliente existe
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado"));

        // Verificar que el cliente pertenece al supermercado
        if (cliente.getSupermercado() == null ||
                !cliente.getSupermercado().getId().equals(supermercado.getId())) {
            throw new ClienteNotFoundException(
                    "El cliente no pertenece al supermercado indicado");
        }

        // Eliminar el cliente
        clienteRepository.delete(cliente);
    }

    @Transactional(readOnly = true)
    public ClienteDTO buscarPorCedula(String cedula, Long supermercadoId) {
        // Verificar que el supermercado existe
        if (!supermercadoRepository.existsById(supermercadoId)) {
            throw new SupermercadoNotFoundExepcion("Supermercado no encontrado");
        }

        // Buscar cliente por cédula y supermercado
        Cliente cliente = clienteRepository
                .findByCedulaAndSupermercadoId(cedula, supermercadoId)
                .orElseThrow(() -> new ClienteNotFoundException(
                        "Cliente con cédula " + cedula + " no encontrado en este supermercado"));

        return clienteMapper.toDto(cliente);
    }
}
