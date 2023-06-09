package ru.orlov.micro.planner.todo.restController;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import ru.orlov.micro.planner.entity.Category;
import ru.orlov.micro.planner.todo.feign.UserFeignClient;
import ru.orlov.micro.planner.todo.search.CategorySearchValues;
import ru.orlov.micro.planner.todo.service.impl.CategoryService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    private final UserFeignClient client;

//    private final WebClientImpl client;

//    private final UserRestBuilder userRestBuilder;

    @PostMapping("/all")
    public List<Category> findAll(@RequestBody final Long id) {
        return categoryService.findAll(id);
    }

    @PostMapping("/add")
    public ResponseEntity<Category> add(@RequestBody final Category category, @AuthenticationPrincipal final Jwt jwt) {
        if (category.getId() != null && category.getId() != 0) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        if (category.getTitle() == null || category.getTitle().trim().length() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

//        // Пример применения restTemplate
//        if (userRestBuilder.userExists(category.getUserId())) { // вызываем микросервис из другого модуля
//            return ResponseEntity.ok(categoryService.add(category)); // возвращаем добавленный объект с заполненным ID
//        }

//        // Пример применения для WebClient синхронно
//        if (client.userExistMono(category.getUserId())) { // вызываем микросервис из другого модуля
//            return ResponseEntity.ok(categoryService.add(category)); // возвращаем добавленный объект с заполненным ID
//        }

//        // Пример применения для WebClient асинхронно (subscribe тут мы подписываемся на событие на результат выполнения)
//        // Данный запрос будет выполнен почти со 100 % гарантией. Если сервис будет не доступен то он будет выполнен когда сервис освободится
//        client.userExistFlux(category.getUserId()).subscribe(System.out::println);

        // Пример применения для FeignClient синхронно
//        final ResponseEntity<User> user = client.findUserById(category.getUserId());
//        if (user != null && user.getBody() != null) {
//            return ResponseEntity.ok(categoryService.add(category)); // возвращаем добавленный объект с заполненным ID
//        }

        // если пользователя НЕ существует
//        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

//        return ResponseEntity.ok(categoryService.add(category));

        category.setUserId(jwt.getSubject()); // UUID пользователя из KeyCloak
        if (category.getUserId() == null || category.getUserId().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(category);
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
    public ResponseEntity<List<Category>> search(@RequestBody final CategorySearchValues categorySearchValues, @AuthenticationPrincipal final Jwt jwt) {
        categorySearchValues.setUserId(jwt.getSubject());
        if (categorySearchValues.getUserId() == null || categorySearchValues.getUserId().isEmpty()) {
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