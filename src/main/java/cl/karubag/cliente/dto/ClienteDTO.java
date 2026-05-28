package cl.karubag.cliente.dto;

import cl.karubag.cliente.model.TipoCliente;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ClienteDTO {

    private Long id;

    @NotNull(message = "El usuarioId es obligatorio")
    private Long usuarioId;

    @NotNull(message = "El planId es obligatorio")
    private Long planId;

    @NotNull(message = "El tipo de cliente es obligatorio")
    private TipoCliente tipoCliente;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombreCompleto;

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El email no es válido")
    private String email;

    private String telefono;

    private Boolean activo;

    public ClienteDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public Long getPlanId() { return planId; }
    public void setPlanId(Long planId) { this.planId = planId; }
    public TipoCliente getTipoCliente() { return tipoCliente; }
    public void setTipoCliente(TipoCliente tipoCliente) { this.tipoCliente = tipoCliente; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}
