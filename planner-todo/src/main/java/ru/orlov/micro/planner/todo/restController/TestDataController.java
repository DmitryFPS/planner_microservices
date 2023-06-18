package ru.orlov.micro.planner.todo.restController;

// для заполнения тестовыми данными

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.orlov.micro.planner.entity.Category;
import ru.orlov.micro.planner.entity.Priority;
import ru.orlov.micro.planner.entity.Task;
import ru.orlov.micro.planner.todo.service.impl.CategoryService;
import ru.orlov.micro.planner.todo.service.impl.PriorityService;
import ru.orlov.micro.planner.todo.service.impl.TaskService;

import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping("/data")
@RequiredArgsConstructor
public class TestDataController {
    private final TaskService taskService;
    private final PriorityService priorityService;
    private final CategoryService categoryService;

    @PostMapping("/init")
    public ResponseEntity<Boolean> init(@RequestBody final Long userId, @AuthenticationPrincipal final Jwt jwt) {

        final Priority prior1 = new Priority();
        prior1.setColor("#fff");
        prior1.setTitle("Важный");
        prior1.setUserId(jwt.getSubject());

        final Priority prior2 = new Priority();
        prior2.setColor("#ffе");
        prior2.setTitle("Неважный");
        prior2.setUserId(jwt.getSubject());

        priorityService.add(prior1);
        priorityService.add(prior2);


        final Category cat1 = new Category();
        cat1.setTitle("Работа");
        cat1.setUserId(jwt.getSubject());

        final Category cat2 = new Category();
        cat2.setTitle("Семья");
        cat2.setUserId(jwt.getSubject());

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
        task1.setUserId(jwt.getSubject());

        final Task task2 = new Task();
        task2.setTitle("Поспать");
        task2.setCategory(cat2);
        task2.setCompleted(false);
        task2.setPriority(prior2);
        task2.setTaskDate(oneWeek);
        task2.setUserId(jwt.getSubject());

        taskService.add(task1);
        taskService.add(task2);

        // если пользователя НЕ существует
        return ResponseEntity.ok(true);
    }
}