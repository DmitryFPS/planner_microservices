package ru.orlov.micro.planner.todo.feign;

//import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.orlov.micro.planner.entity.User;

//@FeignClient(name = "planner-users", fallback = UserFeignClientFallback.class)
public interface UserFeignClient {
    @PostMapping("/user/id")
    ResponseEntity<User> findUserById(final @RequestBody Long id);
}

@Component
class UserFeignClientFallback implements UserFeignClient {
    @Override
    public ResponseEntity<User> findUserById(final Long id) {
        return new ResponseEntity<>(new User(), HttpStatus.BAD_REQUEST); // Этот чисто для проверки, действительно ли этот метод вызывается!!!!
    }
}