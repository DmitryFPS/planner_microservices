package ru.orlov.micro.planner.todo.mq.legacy.impl;

import lombok.RequiredArgsConstructor;
//import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import ru.orlov.micro.planner.todo.mq.legacy.cannels.TodoBinding;
import ru.orlov.micro.planner.utils.tuple.Pair;

//@RequiredArgsConstructor
//@EnableBinding(TodoBinding.class)
//@Component
public class MessageProducer {
//    private final TodoBinding todoBinding;
//
//    // Проверяем успешно ли создались тестовые данные для юзера
//    public void checkAddUser(final Pair<Boolean, Long> p) {
//        final Pair<Boolean, Long> pair = new Pair<>();
//        pair.setFirst(p.getFirst());
//        pair.setSecond(p.getSecond());
//
//        final Message<Pair<Boolean, Long>> message = MessageBuilder.withPayload(pair).build();
//        todoBinding.todoOutputChannelCheckAddUser().send(message);
//    }
}