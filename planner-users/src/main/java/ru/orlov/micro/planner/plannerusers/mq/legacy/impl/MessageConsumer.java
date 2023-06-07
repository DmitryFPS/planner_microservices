package ru.orlov.micro.planner.plannerusers.mq.legacy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;
import ru.orlov.micro.planner.plannerusers.mq.legacy.channels.TodoBinding;
import ru.orlov.micro.planner.plannerusers.service.impl.UserServiceImpl;
import ru.orlov.micro.planner.utils.tuple.Pair;

//@RequiredArgsConstructor
//@EnableBinding(TodoBinding.class)
//@Component
public class MessageConsumer {

//    private final UserServiceImpl service;
//
//    // Для проверки успешно ли создались тодо тестовые данные и если нет то возвращяем фолс и удаляем Юзера
//    @StreamListener(target = TodoBinding.INPUT_CHANNEL_CHECK_ADD_USER)
//    public void checkAddUser(final Pair<Boolean, Long> p) {
//        service.deleteUserIsException(p);
//    }
}