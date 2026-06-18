package org.example.spring_lab3_notifications;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "org.example")
@EntityScan(basePackages = "org.example.model.entity")
@EnableJpaRepositories(basePackages = "org.example.repository")
public class SpringLab3NotificationsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringLab3NotificationsApplication.class, args);
    }
}