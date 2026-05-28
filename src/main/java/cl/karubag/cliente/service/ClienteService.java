package cl.karubag.cliente.service;

import cl.karubag.cliente.dto.ClienteDTO;
import cl.karubag.cliente.model.Cliente;
import cl.karubag.cliente.model.TipoCliente;
import cl.karubag.cliente.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<ClienteDTO> listarTodos() {
        return clienteRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ClienteDTO> listarActivos() {
        return clienteRepository.findByActivoTrue()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ClienteDTO> listarPorTipo(TipoCliente tipo) {
        return clienteRepository.findByTipoCliente(tipo)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ClienteDTO> listarPorPlan(Long planId) {
        return clienteRepository.findByPlanId(planId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ClienteDTO obtenerPorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + id));
        return toDTO(cliente);
    }

    public ClienteDTO crear(ClienteDTO dto) {
        if (clienteRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Ya existe un cliente con el email: " + dto.getEmail());
        }
        return toDTO(clienteRepository.save(toEntity(dto)));
    }

    public ClienteDTO actualizar(Long id, ClienteDTO dto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + id));
        cliente.setNombreCompleto(dto.getNombreCompleto());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefono(dto.getTelefono());
        cliente.setTipoCliente(dto.getTipoCliente());
        cliente.setPlanId(dto.getPlanId());
        cliente.setActivo(dto.getActivo());
        return toDTO(clienteRepository.save(cliente));
    }

    public void eliminar(Long id) {
        clienteRepository.deleteById(id);
    }

    private ClienteDTO toDTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setUsuarioId(cliente.getUsuarioId());
        dto.setPlanId(cliente.getPlanId());
        dto.setTipoCliente(cliente.getTipoCliente());
        dto.setNombreCompleto(cliente.getNombreCompleto());
        dto.setEmail(cliente.getEmail());
        dto.setTelefono(cliente.getTelefono());
        dto.setActivo(cliente.getActivo());
        return dto;
    }

    private Cliente toEntity(ClienteDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setUsuarioId(dto.getUsuarioId());
        cliente.setPlanId(dto.getPlanId());
        cliente.setTipoCliente(dto.getTipoCliente());
        cliente.setNombreCompleto(dto.getNombreCompleto());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefono(dto.getTelefono());
        cliente.setActivo(dto.getActivo() != null ? dto.getActivo() : true);
        return cliente;
    }
}
