package ru.orlov.micro.planner.todo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.orlov.micro.planner.entity.Statistic;

@Repository
public interface StatisticRepository extends CrudRepository<Statistic, Long> {
    Statistic findByUserId(final Long id);
}