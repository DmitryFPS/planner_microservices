package ru.orlov.micro.planner.todo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.orlov.micro.planner.entity.Category;
import ru.orlov.micro.planner.todo.dao.interfaces.CategoryRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository repository;

    @Cacheable(cacheNames = "categories")
    public List<Category> findAll(final Long id) {
        return repository.findByUserIdOrderByTitleAsc(id);
    }

    public Category add(final Category category) {
        return repository.save(category);
    }

    public Category update(final Category category) {
        return repository.save(category);
    }

    public void deleteById(final Long id) {
        repository.deleteById(id);
    }

    public List<Category> findByTitle(final String text, final Long id) {
        return repository.findByTitle(text, id);
    }

    public Category findById(final Long id) {
        return repository.findById(id).orElse(null);
    }
}