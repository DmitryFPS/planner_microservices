package ru.orlov.micro.planner.plannerusers.mq.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import ru.orlov.micro.planner.plannerusers.mq.channels.TodoBinding;

@RequiredArgsConstructor
@EnableBinding(TodoBinding.class)
@Component
public class MessageProducer {
    private final TodoBinding todoBinding;

    public void newUserAction(final Long id) {
        final Message<Long> message = MessageBuilder.withPayload(id).build();
        todoBinding.todoOutputChannel().send(message);
    }
}