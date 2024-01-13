package com.example.taskmanage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableElasticsearchRepositories(basePackages = "com.example.taskmanage.elarepository")
//@EnableJpaRepositories(basePackages = "com.example.taskmanage.repository")
public class TaskManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskManageApplication.class, args);
    }

}
