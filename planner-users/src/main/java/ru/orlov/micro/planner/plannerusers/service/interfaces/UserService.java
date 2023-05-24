package ru.orlov.micro.planner.plannerusers.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ru.orlov.micro.planner.entity.User;

public interface UserService {
    User add(final User user);

    User update(final User user);

    void deleteByUserId(final Long id);

    void deleteByUserEmail(final String email);

    User findById(final Long id);

    User findByEmail(final String email);

    Page<User> findByParam(final String email,
                           final String username,
                           final PageRequest pageRequest);
}