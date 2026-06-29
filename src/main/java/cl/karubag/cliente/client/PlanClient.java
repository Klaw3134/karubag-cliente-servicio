package cl.karubag.cliente.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class PlanClient {

    private final WebClient webClient;

    public PlanClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://karubag-plan-servicio.onrender.com")
                .build();
    }

    public boolean existePlan(Long planId) {
        try {
            webClient.get()
                    .uri("/api/planes/" + planId)
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
