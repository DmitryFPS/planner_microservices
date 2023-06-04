package ru.orlov.micro.planner.todo.mq.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;
import ru.orlov.micro.planner.todo.mq.cannels.TodoBinding;
import ru.orlov.micro.planner.todo.service.impl.TestDataService;

@RequiredArgsConstructor
@EnableBinding(TodoBinding.class)
@Component
public class MessageConsumer {
    private final TestDataService dataService;

    @StreamListener(target = TodoBinding.INPUT_CHANNEL)
    public void initTestData(final Long id) {
        dataService.initTestData(id);
    }
}