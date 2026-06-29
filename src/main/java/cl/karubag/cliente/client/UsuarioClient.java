package cl.karubag.cliente.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class UsuarioClient {

    private final WebClient webClient;

    public UsuarioClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://karubag-usuario-servicio.onrender.com")
                .build();
    }

    public boolean existeUsuario(Long usuarioId) {
        try {
            webClient.get()
                    .uri("/api/v1/usuarios/" + usuarioId)
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
