package ru.orlov.micro.planner.todo.mq.legacy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;
import ru.orlov.micro.planner.todo.mq.legacy.cannels.TodoBinding;
import ru.orlov.micro.planner.todo.service.impl.TestDataService;

//@RequiredArgsConstructor
//@EnableBinding(TodoBinding.class)
//@Component
public class MessageConsumer {
//    private final TestDataService dataService;
//
//    // Автоматически будет вызван, когда в очередь попадет сообщение для входного канала todoInputChannel
//    @StreamListener(target = TodoBinding.INPUT_CHANNEL)
//    public void initTestData(final Long id) {
//        dataService.initTestData(id);
//    }
}