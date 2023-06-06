package ru.orlov.micro.planner.todo.mq.cannels;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

public interface TodoBinding {
    String INPUT_CHANNEL = "todoInputChannel";

    // Канал для приема сообщения! Как в очереди появится сообщение то он сразу же его прочетает
    @Input(INPUT_CHANNEL)
    MessageChannel todoInputChannel();
}