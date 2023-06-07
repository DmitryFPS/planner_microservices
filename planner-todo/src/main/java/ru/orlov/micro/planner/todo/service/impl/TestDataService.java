package ru.orlov.micro.planner.todo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.orlov.micro.planner.entity.Category;
import ru.orlov.micro.planner.entity.Priority;
import ru.orlov.micro.planner.entity.Task;
import ru.orlov.micro.planner.todo.mq.legacy.impl.MessageProducer;

import java.util.Calendar;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TestDataService {

    private final TaskService taskService;
    private final PriorityService priorityService;
    private final CategoryService categoryService;

    private final MessageProducer producer;

    public void initTestData(final Long userId) {

        final Priority prior1 = new Priority();
        prior1.setColor("#fff");
        prior1.setTitle("Важный");
        prior1.setUserId(userId);

        final Priority prior2 = new Priority();
        prior2.setColor("#ffе");
        prior2.setTitle("Неважный");
        prior2.setUserId(userId);

        priorityService.add(prior1);
        priorityService.add(prior2);


        final Category cat1 = new Category();
        cat1.setTitle("Работа");
        cat1.setUserId(userId);

        final Category cat2 = new Category();
        cat2.setTitle("Семья");
        cat2.setUserId(userId);

        categoryService.add(cat1);
        categoryService.add(cat2);

        // завтра
        Date tomorrow = new Date();
        final Calendar c = Calendar.getInstance();
        c.setTime(tomorrow);
        c.add(Calendar.DATE, 1);
        tomorrow = c.getTime();

        // неделя
        Date oneWeek = new Date();
        final Calendar c2 = Calendar.getInstance();
        c2.setTime(oneWeek);
        c2.add(Calendar.DATE, 7);
        oneWeek = c2.getTime();

        final Task task1 = new Task();
        task1.setTitle("Покушать");
        task1.setCategory(cat1);
        task1.setPriority(prior1);
        task1.setCompleted(true);
        task1.setTaskDate(tomorrow);
        task1.setUserId(userId);

        final Task task2 = new Task();
        task2.setTitle("Поспать");
        task2.setCategory(cat2);
        task2.setCompleted(false);
        task2.setPriority(prior2);
        task2.setTaskDate(oneWeek);
        task2.setUserId(userId);

        taskService.add(task1);
        taskService.add(task2);


        // Для отката юзера в случае ошибки (Тест)
//        boolean hasError = false;
//        try {
//            throw new Exception("Test");
//        } catch (Exception ext) {
//            hasError = true;
//        }
//        final Pair<Boolean, Long> pair = new Pair<>(false, userId);
//        producer.checkAddUser(pair);
    }
}