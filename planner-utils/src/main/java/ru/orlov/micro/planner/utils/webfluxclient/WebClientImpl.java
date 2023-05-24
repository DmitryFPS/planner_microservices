package ru.orlov.micro.planner.utils.webfluxclient;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import ru.orlov.micro.planner.entity.User;

@Component
public class WebClientImpl {
    private static final String BASE_URL = "http://localhost:8082/planner-users/user/";
    final WebClient client = WebClient.create(BASE_URL);

    public boolean userExistMono(final Long id) {
        final User getUser = client
                .post()
                .uri("/id")
                .bodyValue(id)
                .retrieve()
                .bodyToMono(User.class)
                .block();
        return getUser != null;
    }

    public Flux<User> userExistFlux(final Long id) {
        return client
                .post()
                .uri("/id")
                .bodyValue(id)
                .retrieve()
                .bodyToFlux(User.class);
    }
}