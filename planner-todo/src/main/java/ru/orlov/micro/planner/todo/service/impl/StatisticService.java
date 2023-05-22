package ru.orlov.micro.planner.todo.service.impl;

import org.springframework.stereotype.Service;
import ru.orlov.micro.planner.entity.Statistic;
import ru.orlov.micro.planner.todo.dao.interfaces.StatisticRepository;

import javax.transaction.Transactional;

@Service
@Transactional
public class StatisticService {
    private final StatisticRepository repository;

    public StatisticService(final StatisticRepository repository) {
        this.repository = repository;
    }

    public Statistic findStat(final Long id) {
        return repository.findByUserId(id);
    }
}