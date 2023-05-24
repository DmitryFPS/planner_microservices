package ru.orlov.micro.planner.plannerusers.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.orlov.micro.planner.entity.User;
import ru.orlov.micro.planner.plannerusers.repository.UserRepository;
import ru.orlov.micro.planner.plannerusers.service.interfaces.UserService;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public User add(final User user) {
        return repository.save(user);
    }

    @Override
    public User update(final User user) {
        return repository.save(user);
    }

    @Override
    public void deleteByUserId(final Long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteByUserEmail(final String email) {
        repository.deleteByEmail(email);
    }

    @Override
    public User findById(final Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public User findByEmail(final String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Page<User> findByParam(final String email,
                                  final String username,
                                  final PageRequest pageRequest) {
        return repository.findByParams(email, username, pageRequest);
    }
}