package ru.orlov.micro.planner.plannerusers.mq.func;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.util.concurrent.Queues;

import java.util.function.Supplier;

@Getter
@RequiredArgsConstructor
@Configuration
public class MessageFunc {

    // Создаем внутреннюю шину в которую будем класть сообщение и которая будет сигнализировать что есть сообщение для отправки
    private final Sinks.Many<Message<Long>> innerBus = Sinks.many().multicast().onBackpressureBuffer(Queues.SMALL_BUFFER_SIZE, false);

    // Создаем бин который Supplier будет автоматом перехватывать внутреннюю шину с сообщением если оно есть и отправлять в SCS
    @Bean
    public Supplier<Flux<Message<Long>>> newUserActionProducer() {
        return innerBus::asFlux;
    }
}