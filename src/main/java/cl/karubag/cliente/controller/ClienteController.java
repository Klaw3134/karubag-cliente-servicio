package cl.karubag.cliente.controller;

import cl.karubag.cliente.dto.ClienteDTO;
import cl.karubag.cliente.model.TipoCliente;
import cl.karubag.cliente.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listarTodos() {
        return ResponseEntity.ok(clienteService.listarTodos());
    }

    @GetMapping("/activos")
    public ResponseEntity<List<ClienteDTO>> listarActivos() {
        return ResponseEntity.ok(clienteService.listarActivos());
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<ClienteDTO>> listarPorTipo(@PathVariable TipoCliente tipo) {
        return ResponseEntity.ok(clienteService.listarPorTipo(tipo));
    }

    @GetMapping("/plan/{planId}")
    public ResponseEntity<List<ClienteDTO>> listarPorPlan(@PathVariable Long planId) {
        return ResponseEntity.ok(clienteService.listarPorPlan(planId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> crear(@Valid @RequestBody ClienteDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> actualizar(@PathVariable Long id, @Valid @RequestBody ClienteDTO dto) {
        return ResponseEntity.ok(clienteService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
