package ru.orlov.micro.planner.todo.mq.cannels;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

public interface TodoBinding {
    String INPUT_CHANNEL = "todoInputChannel";

    @Input(INPUT_CHANNEL)
    MessageChannel todoInputChannel();
}