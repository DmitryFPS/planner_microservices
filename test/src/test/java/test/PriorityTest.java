package test;

import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;
import ru.orlov.micro.planner.entity.Priority;
import ru.orlov.micro.planner.todo.service.impl.PriorityService;

@Component
public class PriorityTest {
    private PriorityService priorityService;

    @Test
    void testPriority() {
        final Priority priority = new Priority();
        priority.setColor("#fff");
        priority.setTitle("Важный");
        priority.setUserId(2L);

        priorityService.add(priority);
    }
}