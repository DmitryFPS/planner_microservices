package ru.orlov.micro.planner.plannerusers.mq.legacy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import ru.orlov.micro.planner.plannerusers.mq.legacy.channels.TodoBinding;

//@RequiredArgsConstructor
//@EnableBinding(TodoBinding.class)
//@Component
public class MessageProducer {
//    private final TodoBinding todoBinding;
//
//    // Помещаем в сообщение полезную нагрузку и отправляем в RabbitMq
//    public void initUserAction(final Long id) {
//        final Message<Long> message = MessageBuilder.withPayload(id).build();
//        todoBinding.todoOutputChannel().send(message);
//    }
}