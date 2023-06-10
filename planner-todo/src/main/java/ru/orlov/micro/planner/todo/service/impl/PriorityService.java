package ru.orlov.micro.planner.todo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.orlov.micro.planner.entity.Priority;
import ru.orlov.micro.planner.todo.repository.PriorityRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PriorityService {
    private final PriorityRepository repository;

    public List<Priority> findAll(final Long id) {
        return repository.findByUserIdOrderByIdAsc(id);
    }

    public Priority add(final Priority priority) {
        final Priority priority1 = repository.save(priority);
        return priority1;
    }

    public Priority update(final Priority priority) {
        return repository.save(priority);
    }

    public void deleteById(final Long id) {
        repository.deleteById(id);
    }

    public Priority findById(final Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Priority> find(final String title, final Long id) {
        return repository.findByTitle(title, id);
    }
}