package ru.orlov.micro.planner.plannerusers.restController;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.orlov.micro.planner.entity.User;
import ru.orlov.micro.planner.plannerusers.search.UserSearchValues;
import ru.orlov.micro.planner.plannerusers.service.interfaces.UserService;

import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    public static final String ID_COLUMN = "id";
    private final UserService service;

    @PostMapping("/add")
    public ResponseEntity<User> add(@RequestBody final User user) {
        if (user.getId() != null && user.getId() != 0) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        if (user.getEmail() == null || user.getEmail().trim().length() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        if (user.getUserName() == null || user.getUserName().trim().length() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        if (user.getPassword() == null || user.getPassword().trim().length() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        User addingUser;
        try {
            addingUser = service.add(user);
        } catch (final EmptyResultDataAccessException ext) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(addingUser);
    }

    @PutMapping("/update")
    public ResponseEntity<User> update(@RequestBody final User user) {
        if (user.getId() == null || user.getId() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        if (user.getEmail() == null || user.getEmail().trim().length() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        if (user.getUserName() == null || user.getUserName().trim().length() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        if (user.getPassword() == null || user.getPassword().trim().length() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        User addingUser;
        try {
            addingUser = service.add(user);
        } catch (final EmptyResultDataAccessException ext) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(addingUser);
    }

    @DeleteMapping("/delete/id")
    public ResponseEntity<Void> deleteByUserId(@RequestBody final Long id) {
        try {
            service.deleteByUserId(id);
        } catch (final EmptyResultDataAccessException ext) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/email")
    public ResponseEntity<Void> deleteByUserEmail(@RequestBody final String email) {
        try {
            service.deleteByUserEmail(email);
        } catch (final EmptyResultDataAccessException ext) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/id")
    public ResponseEntity<User> findById(@RequestBody final Long id) {
        User user;
        try {
//            Thread.sleep(10_000L); // для имитации временного не доступа к сервису
            user = service.findById(id);
        } catch (final NoSuchElementException ext) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
//        catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/email")
    public ResponseEntity<User> findById(@RequestBody final String email) {
        User user;
        try {
            user = service.findByEmail(email);
        } catch (final NoSuchElementException ext) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<User>> search(@RequestBody final UserSearchValues userSearchValues) {
        final String email = userSearchValues.getEmail() != null ? userSearchValues.getEmail() : null;
        final String username = userSearchValues.getUsername() != null ? userSearchValues.getUsername() : null;

        if ((email == null || email.trim().length() == 0) && (username == null || username.trim().length() == 0)) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        final String sortColumn = userSearchValues.getSortColumn() != null ? userSearchValues.getSortColumn() : null;
        final String sortDirection = userSearchValues.getSortDirection() != null ? userSearchValues.getSortDirection() : null;

        final int pageNumber = userSearchValues.getPageNumber() != null ? userSearchValues.getPageNumber() : 0;
        final int pageSize = userSearchValues.getPageSize() != null ? userSearchValues.getPageSize() : 10;

        final Sort.Direction direction = sortDirection == null || sortDirection.trim().length() == 0 || sortDirection.trim().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        final Sort sort = Sort.by(direction, sortColumn, ID_COLUMN);
        final PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        final Page<User> result = service.findByParam(email, username, pageRequest);
        return ResponseEntity.ok(result);
    }
}