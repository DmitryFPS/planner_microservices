package ru.orlov.app.plannerusers.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.orlov.micro.planner.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(final String email);

    void deleteByEmail(final String email);

    @Query("select u from User u where " +
            ":email is null or :email = '' or lower(u.email) like lower(concat('%', :email, '%')) or" +
            " (:username is null or :username = '' or lower(u.userName) like lower(concat('%', :username, '%'))) ")
    Page<User> findByParams(@Param("email") final String email,
                            @Param("username") final String username,
                            final Pageable pageable);
}