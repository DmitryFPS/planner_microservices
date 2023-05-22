package ru.orlov.micro.planner.todo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.orlov.micro.planner.entity.Task;
import ru.orlov.micro.planner.todo.dao.interfaces.TaskRepository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository repository; // сервис имеет право обращаться к репозиторию (БД)

    @Cacheable(cacheNames = "tasks")
    public List<Task> findAll(final Long id) {
        return repository.findByUserIdOrderByTitleAsc(id);
    }

    public Task add(final Task task) {
        return repository.save(task); // метод save обновляет или создает новый объект, если его не было
    }

    public Task update(final Task task) {
        return repository.save(task); // метод save обновляет или создает новый объект, если его не было
    }

    public void deleteById(final Long id) {
        repository.deleteById(id);
    }

    @Cacheable(cacheNames = "tasks")
    public Page<Task> findByParams(final String text,
                                   final Integer completed,
                                   final Long priorityId,
                                   final Long categoryId,
                                   final Long userId,
                                   final Date dateFrom,
                                   final Date dateTo,
                                   final PageRequest paging) {
        return repository.findByParams(text, completed, priorityId, categoryId, userId, dateFrom, dateTo, paging);
    }

    public Task findById(Long id) {
        return repository.findById(id).orElse(null);
    }
}