package ru.orlov.micro.planner.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"ru.orlov.micro.planner"})
@EnableJpaRepositories(basePackages = {"ru.orlov.micro.planner.todo.repository"})
@EntityScan(basePackages = {"ru.orlov.micro.planner.entity"})
public class PlannerTodoApplication {
    public static void main(String[] args) {
        SpringApplication.run(PlannerTodoApplication.class, args);
    }
}