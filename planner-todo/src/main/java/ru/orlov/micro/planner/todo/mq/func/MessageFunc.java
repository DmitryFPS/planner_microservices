package ru.orlov.micro.planner.todo.mq.func;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import ru.orlov.micro.planner.todo.service.impl.TestDataService;

import java.util.function.Consumer;

@Getter
@RequiredArgsConstructor
@Configuration
public class MessageFunc { // Канал для принятия сообщений от Supplier

    // Инжектим сервис с тестовыми данными
    private final TestDataService dataService;

    // Этот бин будет получать сообщение и вызывать сервис с методом создания тестовых данных и передавать в него id Юзера и тем самым инициировать создание тестовых данных
    @Bean(name = "newUserActionConsumer")
    public Consumer<Message<Long>> newUserActionConsumer()  {
        return message -> {
            try {
                dataService.initTestData(message.getPayload());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}