package ru.orlov.micro.planner.todo.restController;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.orlov.micro.planner.entity.Task;
import ru.orlov.micro.planner.todo.search.TaskSearchValues;
import ru.orlov.micro.planner.todo.service.impl.TaskService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    public static final String ID_COLUMN = "id";
    private final TaskService taskService;

    @PostMapping("/all")
    public ResponseEntity<List<Task>> findAll(@RequestBody final Long id) {
        return ResponseEntity.ok(taskService.findAll(id));
    }

    // добавление
    @PostMapping("/add")
    public ResponseEntity<Task> add(@RequestBody final Task task) {
        if (task.getId() != null && task.getId() != 0) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        if (task.getTitle() == null || task.getTitle().trim().length() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(taskService.add(task));
    }

    @PutMapping("/update")
    public ResponseEntity<Task> update(@RequestBody final Task task) {
        if (task.getId() == null || task.getId() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        if (task.getTitle() == null || task.getTitle().trim().length() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        final Task updatedTask = taskService.update(task);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") final Long id) {
        try {
            taskService.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/id")
    public ResponseEntity<Task> findById(@RequestBody final Long id) {
        Task task;
        try {
            task = taskService.findById(id);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(task);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<Task>> search(@RequestBody final TaskSearchValues taskSearchValues) {

        // исключить NullPointerException
        final String title = taskSearchValues.getTitle() != null ? taskSearchValues.getTitle() : null;

        // конвертируем Boolean в Integer
        final Boolean completed = taskSearchValues.getCompleted() != null && taskSearchValues.getCompleted() == 1;

        final Long priorityId = taskSearchValues.getPriorityId() != null ? taskSearchValues.getPriorityId() : null;
        final Long categoryId = taskSearchValues.getCategoryId() != null ? taskSearchValues.getCategoryId() : null;

        final String sortColumn = taskSearchValues.getSortColumn() != null ? taskSearchValues.getSortColumn() : null;
        final String sortDirection = taskSearchValues.getSortDirection() != null ? taskSearchValues.getSortDirection() : null;

        final Integer pageNumber = taskSearchValues.getPageNumber() != null ? taskSearchValues.getPageNumber() : null;
        final Integer pageSize = taskSearchValues.getPageSize() != null ? taskSearchValues.getPageSize() : null;

        final Long userId = taskSearchValues.getUserId() != null ? taskSearchValues.getUserId() : null;

        // проверка на обязательные параметры
        if (userId == null || userId == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        // чтобы захватить в выборке все задачи по датам, независимо от времени - можно выставить время с 00:00 до 23:59
        Date dateFrom = null;
        Date dateTo = null;

        // выставить 00:01 для начальной даты (если она указана)
        if (taskSearchValues.getDateFrom() != null) {
            final Calendar calendarFrom = Calendar.getInstance();
            calendarFrom.setTime(taskSearchValues.getDateFrom());
            calendarFrom.set(Calendar.HOUR_OF_DAY, 0);
            calendarFrom.set(Calendar.MINUTE, 1);
            calendarFrom.set(Calendar.SECOND, 1);
            calendarFrom.set(Calendar.MILLISECOND, 1);
            dateFrom = calendarFrom.getTime(); // записываем начальную дату с 00:01
        }

        // выставить 23:59 для конечной даты (если она указана)
        if (taskSearchValues.getDateTo() != null) {
            final Calendar calendarTo = Calendar.getInstance();
            calendarTo.setTime(taskSearchValues.getDateTo());
            calendarTo.set(Calendar.HOUR_OF_DAY, 23);
            calendarTo.set(Calendar.MINUTE, 59);
            calendarTo.set(Calendar.SECOND, 59);
            calendarTo.set(Calendar.MILLISECOND, 999);
            dateTo = calendarTo.getTime(); // записываем конечную дату с 23:59
        }

        // направление сортировки
        final Sort.Direction direction = sortDirection == null || sortDirection.trim().length() == 0 || sortDirection.trim().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        /* Вторым полем для сортировки добавляем id, чтобы всегда сохранялся строгий порядок.
            Например, если у 2-х задач одинаковое значение приоритета и мы сортируем по этому полю.
            Порядок следования этих 2-х записей после выполнения запроса может каждый раз меняться, т.к. не указано второе поле сортировки.
            Поэтому и используем ID - тогда все записи с одинаковым значением приоритета будут следовать в одном порядке по ID.
         */

        // объект сортировки, который содержит столбец и направление
        final Sort sort = Sort.by(direction, sortColumn, ID_COLUMN);

        // объект по страничности (пагинация)
        final PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        // результат запроса с постраничным выводом
        final Page<Task> result = taskService.findByParams(title, completed, priorityId, categoryId, userId, dateFrom, dateTo, pageRequest);

        // результат запроса
        return ResponseEntity.ok(result);
    }
}