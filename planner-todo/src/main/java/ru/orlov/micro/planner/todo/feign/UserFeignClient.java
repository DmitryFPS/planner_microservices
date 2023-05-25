package ru.orlov.micro.planner.todo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.orlov.micro.planner.entity.User;

@FeignClient(name = "planner-users")
public interface UserFeignClient {
    @PostMapping("/user/id")
    ResponseEntity<User> findUserById(final @RequestBody Long id);
}