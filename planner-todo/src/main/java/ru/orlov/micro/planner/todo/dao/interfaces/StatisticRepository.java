package ru.orlov.micro.planner.todo.dao.interfaces;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.orlov.micro.planner.entity.Statistic;

@Repository
public interface StatisticRepository extends CrudRepository<Statistic, Long> {
    Statistic findByUserId(final Long id);
}