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

@RestController
@RequiredArgsConstructor
@RequestMapping("/category") // базовый URI
public class CategoryController {

    // доступ к данным из БД
    private final CategoryService categoryService;

    @PostMapping("/all")
    public List<Category> findAll(@RequestBody final Long id) {
        return categoryService.findAll(id);
    }

    @PostMapping("/add")
    public ResponseEntity<Category> add(@RequestBody final Category category) {
        // проверка на обязательные параметры
        if (category.getId() != null && category.getId() != 0) { // это означает, что id заполнено
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        // если передали пустое значение title
        if (category.getTitle() == null || category.getTitle().trim().length() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(categoryService.add(category)); // возвращаем добавленный объект с заполненным ID
    }

    @PutMapping("/update")
    public ResponseEntity<Category> update(@RequestBody final Category category) {
        // проверка на обязательные параметры
        if (category.getId() == null || category.getId() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        // если передали пустое значение title
        if (category.getTitle() == null || category.getTitle().trim().length() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        // save работает как на добавление, так и на обновление
        final Category updatedCategory = categoryService.update(category);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK); // просто отправляем статус 200 (операция прошла успешно)
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") final Long id) {
        try {
            categoryService.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(HttpStatus.OK); // просто отправляем статус 200 без объектов (операция прошла успешно)
    }

    // поиск по любым параметрам CategorySearchValues
    @PostMapping("/search")
    public ResponseEntity<List<Category>> search(@RequestBody final CategorySearchValues categorySearchValues) {
        // проверка на обязательные параметры
        if (categorySearchValues.getUserId() == null || categorySearchValues.getUserId() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        // поиск категорий пользователя по названию
        List<Category> list = categoryService.findByTitle(categorySearchValues.getTitle(), categorySearchValues.getUserId());
        return ResponseEntity.ok(list);
    }

    // параметр id передаются не в BODY запроса, а в самом URL
    @PostMapping("/id")
    public ResponseEntity<Category> findById(@RequestBody final Long id) {
        Category category;
        try {
            category = categoryService.findById(id);
        } catch (final IllegalArgumentException ext) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(category, HttpStatus.OK);
    }
}