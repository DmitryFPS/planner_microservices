package ru.orlov.micro.planner.plannerusers.mq.channels;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface TodoBinding {
    String OUTPUT_CHANNEL = "todoOutputChannel";

    // Канал на отправку сообщения в RabbitMq
    @Output(OUTPUT_CHANNEL)
    MessageChannel todoOutputChannel();
}