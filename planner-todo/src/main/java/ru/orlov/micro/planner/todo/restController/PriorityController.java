package ru.orlov.micro.planner.todo.restController;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.orlov.micro.planner.entity.Priority;
import ru.orlov.micro.planner.todo.search.PrioritySearchValues;
import ru.orlov.micro.planner.todo.service.impl.PriorityService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/priority")
@RequiredArgsConstructor
public class PriorityController {

    private final PriorityService priorityService;

    @PostMapping("/all")
    public List<Priority> findAll(@RequestBody final Long id) {
        return priorityService.findAll(id);
    }

    @PostMapping("/add")
    public ResponseEntity<Priority> add(@RequestBody final Priority priority) {
        if (priority.getId() != null && priority.getId() != 0) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        if (priority.getTitle() == null || priority.getTitle().trim().length() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        if (priority.getColor() == null || priority.getColor().trim().length() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(priorityService.add(priority));
    }

    @PutMapping("/update")
    public ResponseEntity<Priority> update(@RequestBody final Priority priority) {
        if (priority.getId() == null || priority.getId() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        if (priority.getTitle() == null || priority.getTitle().trim().length() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        if (priority.getColor() == null || priority.getColor().trim().length() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        final Priority updatedPriority = priorityService.update(priority);
        return new ResponseEntity<>(updatedPriority, HttpStatus.OK);
    }

    @PostMapping("/id")
    public ResponseEntity<Priority> findById(@RequestBody Long id) {
        Priority priority;
        try {
            priority = priorityService.findById(id);
        } catch (NoSuchElementException ext) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(priority);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        try {
            priorityService.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Priority>> search(@RequestBody PrioritySearchValues prioritySearchValues) {
        if (prioritySearchValues.getUserId() == null || prioritySearchValues.getUserId() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(priorityService.find(prioritySearchValues.getTitle(), prioritySearchValues.getUserId()));
    }
}