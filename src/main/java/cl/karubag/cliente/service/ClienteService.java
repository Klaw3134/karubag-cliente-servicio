package cl.karubag.cliente.service;

import cl.karubag.cliente.dto.ClienteDTO;
import cl.karubag.cliente.model.Cliente;
import cl.karubag.cliente.model.TipoCliente;
import cl.karubag.cliente.repository.ClienteRepository;
import cl.karubag.cliente.exception.ResourceNotFoundException; 
import cl.karubag.cliente.exception.DuplicateResourceException;
import cl.karubag.cliente.client.UsuarioClient;
import cl.karubag.cliente.client.PlanClient;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final UsuarioClient usuarioClient;
    private final PlanClient planClient;

public ClienteService(ClienteRepository clienteRepository,
                    UsuarioClient usuarioClient,
                    PlanClient planClient) {
    this.clienteRepository = clienteRepository;
    this.usuarioClient = usuarioClient;
    this.planClient = planClient;
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
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + id));
        return toDTO(cliente);
    }

    public ClienteDTO crear(ClienteDTO dto) {
        // ===== VALIDACIONES CRUZADAS via WebClient =====
        if (!usuarioClient.existeUsuario(dto.getUsuarioId())) {
            throw new ResourceNotFoundException(
                "No existe el usuario con id " + dto.getUsuarioId() + " en usuario-servicio");
        }
        if (!planClient.existePlan(dto.getPlanId())) {
            throw new ResourceNotFoundException(
                "No existe el plan con id " + dto.getPlanId() + " en plan-servicio");
        }
        if (clienteRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Ya existe un cliente con el email: " + dto.getEmail());
        }
        return toDTO(clienteRepository.save(toEntity(dto)));
    }

    public ClienteDTO actualizar(Long id, ClienteDTO dto) {
        Cliente cliente = clienteRepository.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + id));
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
