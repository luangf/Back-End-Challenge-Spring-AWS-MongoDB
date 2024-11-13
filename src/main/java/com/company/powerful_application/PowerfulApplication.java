package com.company.powerful_application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class PowerfulApplication {

	public static void main(String[] args) {
		SpringApplication.run(PowerfulApplication.class, args);
	}

}
