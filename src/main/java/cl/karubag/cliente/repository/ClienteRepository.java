package cl.karubag.cliente.repository;

import cl.karubag.cliente.model.Cliente;
import cl.karubag.cliente.model.TipoCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    List<Cliente> findByActivoTrue();

    List<Cliente> findByTipoCliente(TipoCliente tipoCliente);

    Optional<Cliente> findByEmail(String email);

    boolean existsByEmail(String email);

    List<Cliente> findByPlanId(Long planId);
}
