package com.example.springnews;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.springnews"})
@EnableJpaRepositories(basePackages = {"com.example.springnews.repository"})
@EntityScan(basePackages = {"com.example.springnews.model"})
public class SpringnewsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringnewsApplication.class, args);
	}

}
