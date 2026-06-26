package cl.karubag.cliente.controller;

import cl.karubag.cliente.dto.ClienteDTO;
import cl.karubag.cliente.model.TipoCliente;
import cl.karubag.cliente.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Clientes", description = "Gestión de clientes Karübag")
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Operation(summary = "Listar todos los clientes", description = "Retorna la lista completa de clientes")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listarTodos() {
        return ResponseEntity.ok(clienteService.listarTodos());
    }

    @Operation(summary = "Listar clientes activos", description = "Retorna solo los clientes activos")
    @ApiResponse(responseCode = "200", description = "Lista de clientes activos")
    @GetMapping("/activos")
    public ResponseEntity<List<ClienteDTO>> listarActivos() {
        return ResponseEntity.ok(clienteService.listarActivos());
    }

    @Operation(summary = "Listar por tipo", description = "Retorna clientes filtrados por tipo")
    @ApiResponse(responseCode = "200", description = "Lista filtrada por tipo")
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<ClienteDTO>> listarPorTipo(@PathVariable TipoCliente tipo) {
        return ResponseEntity.ok(clienteService.listarPorTipo(tipo));
    }

    @Operation(summary = "Listar por plan", description = "Retorna clientes de un plan específico")
    @ApiResponse(responseCode = "200", description = "Lista de clientes por plan")
    @GetMapping("/plan/{planId}")
    public ResponseEntity<List<ClienteDTO>> listarPorPlan(@PathVariable Long planId) {
        return ResponseEntity.ok(clienteService.listarPorPlan(planId));
    }

    @Operation(summary = "Obtener cliente por ID", description = "Busca un cliente por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.obtenerPorId(id));
    }

    @Operation(summary = "Crear cliente", description = "Crea un nuevo cliente verificando usuario y plan via WebClient")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente",
            content = @Content(schema = @Schema(implementation = ClienteDTO.class),
            examples = @ExampleObject(value = "{\"usuarioId\": 1, \"planId\": 1, \"tipoCliente\": \"RESIDENCIAL\", \"nombreCompleto\": \"Catalina Rojas\", \"email\": \"catalina@karubag.cl\", \"telefono\": \"+56912345678\", \"activo\": true}"))),
        @ApiResponse(responseCode = "404", description = "Usuario o plan no encontrado"),
        @ApiResponse(responseCode = "409", description = "Ya existe un cliente con ese email")
    })
    @PostMapping
    public ResponseEntity<ClienteDTO> crear(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos del cliente a crear",
            required = true,
            content = @Content(examples = @ExampleObject(value = "{\"usuarioId\": 1, \"planId\": 1, \"tipoCliente\": \"RESIDENCIAL\", \"nombreCompleto\": \"Catalina Rojas\", \"email\": \"catalina@karubag.cl\", \"telefono\": \"+56912345678\", \"activo\": true}")))
        @Valid @RequestBody ClienteDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.crear(dto));
    }

    @Operation(summary = "Actualizar cliente", description = "Actualiza los datos de un cliente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> actualizar(@PathVariable Long id, @Valid @RequestBody ClienteDTO dto) {
        return ResponseEntity.ok(clienteService.actualizar(id, dto));
    }

    @Operation(summary = "Eliminar cliente", description = "Elimina un cliente por su ID")
    @ApiResponse(responseCode = "204", description = "Cliente eliminado exitosamente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
