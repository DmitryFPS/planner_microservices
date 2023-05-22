package ru.orlov.micro.planner.todo.restController;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.orlov.micro.planner.entity.Category;
import ru.orlov.micro.planner.todo.search.CategorySearchValues;
import ru.orlov.micro.planner.todo.service.impl.CategoryService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/all")
    public List<Category> findAll(@RequestBody final Long id) {
        return categoryService.findAll(id);
    }

    @PostMapping("/add")
    public ResponseEntity<Category> add(@RequestBody final Category category) {
        if (category.getId() != null && category.getId() != 0) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        if (category.getTitle() == null || category.getTitle().trim().length() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(categoryService.add(category));
    }

    @PutMapping("/update")
    public ResponseEntity<Category> update(@RequestBody final Category category) {
        if (category.getId() == null || category.getId() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        if (category.getTitle() == null || category.getTitle().trim().length() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        final Category updatedCategory = categoryService.update(category);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") final Long id) {
        try {
            categoryService.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Category>> search(@RequestBody final CategorySearchValues categorySearchValues) {
        if (categorySearchValues.getUserId() == null || categorySearchValues.getUserId() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        List<Category> list = categoryService.findByTitle(categorySearchValues.getTitle(), categorySearchValues.getUserId());
        return ResponseEntity.ok(list);
    }

    @PostMapping("/id")
    public ResponseEntity<Category> findById(@RequestBody final Long id) {
        Category category;
        try {
            category = categoryService.findById(id);
        } catch (final NoSuchElementException ext) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(category, HttpStatus.OK);
    }
}