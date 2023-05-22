package ru.orlov.micro.planner.todo.dao.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.orlov.micro.planner.entity.Task;

import java.util.Date;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUserIdOrderByTitleAsc(final Long id);

    @Query("SELECT t FROM Task t where " +
            "(:title is null or :title = '' or lower(t.title) like lower(concat('%', :title,'%'))) and" +
            "(:completed is null or t.completed = :completed) and " +
            "(:priorityId is null or t.priority.id = :priorityId) and " +
            "(:categoryId is null or t.category.id = :categoryId) and " +
            "(:categoryId is null or t.category.id = :categoryId) and " +
            "(" +
            "(cast(:dateFrom as timestamp) is null or t.taskDate >= :dateFrom) and " +
            "(cast(:dateTo as timestamp) is null or t.taskDate <= :dateTo)" +
            ") and " +
            "(t.userId = :id)")
    Page<Task> findByParams(@Param("title") final String title,
                            @Param("completed") final Boolean completed,
                            @Param("priorityId") final Long priorityId,
                            @Param("categoryId") final Long categoryId,
                            @Param("id") final Long id,
                            @Param("dateFrom") final Date dateFrom,
                            @Param("dateTo") final Date dateTo,
                            final Pageable pageable
    );
}