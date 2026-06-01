package cl.karubag.cliente.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuracion de WebClients para llamadas sincronicas a otros microservicios.
 *
 * Permite que cliente-servicio valide cruzadamente contra usuario-servicio y plan-servicio
 * antes de crear o actualizar un cliente. Esto garantiza la consistencia de los datos
 * cuando cada microservicio tiene su propia base de datos independiente.
 */
@Configuration
public class WebClientConfig {

    @Value("${karubag.servicios.usuario.url:http://localhost:8081}")
    private String usuarioServicioUrl;

    @Value("${karubag.servicios.plan.url:http://localhost:8082}")
    private String planServicioUrl;

    @Bean
    public WebClient usuarioWebClient() {
        return WebClient.builder().baseUrl(usuarioServicioUrl).build();
    }

    @Bean
    public WebClient planWebClient() {
        return WebClient.builder().baseUrl(planServicioUrl).build();
    }
}