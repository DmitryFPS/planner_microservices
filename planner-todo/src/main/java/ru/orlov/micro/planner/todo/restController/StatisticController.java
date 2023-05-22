package ru.orlov.micro.planner.todo.restController;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.orlov.micro.planner.entity.Statistic;
import ru.orlov.micro.planner.todo.service.impl.StatisticService;

@RestController
@RequiredArgsConstructor
public class StatisticController {
    private final StatisticService statService;

    @PostMapping("/stat")
    public ResponseEntity<Statistic> findByEmail(@RequestBody final Long id) {
        return ResponseEntity.ok(statService.findStat(id));
    }
}