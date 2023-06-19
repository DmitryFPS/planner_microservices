package ru.orlov.micro.planner.plannerusers.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.orlov.micro.planner.entity.User;
import ru.orlov.micro.planner.plannerusers.keycloak.KeyCloakUtils;

import javax.ws.rs.core.Response;

@RequiredArgsConstructor
@RequestMapping("/admin/user")
@RestController
public class AdminController {
    private final KeyCloakUtils keyCloakUtils;

    @PostMapping("/add")
    public ResponseEntity<Object> add(@RequestBody final User user) {
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

        final Response createResponse = keyCloakUtils.createKeycloakUser(user);
        return ResponseEntity.status(createResponse.getStatus()).body(createResponse.getEntity());
    }
}