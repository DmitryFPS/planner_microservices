package ru.orlov.app.plannerusers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"ru.orlov.micro.planner"})
//@EnableJpaRepositories(basePackages = {"ru.orlov.micro.planner"})
@EntityScan(basePackages = {"ru.orlov.micro.planner.entity"})
public class PlannerUsersApplication {
    public static void main(String[] args) {
        SpringApplication.run(PlannerUsersApplication.class, args);
    }
}