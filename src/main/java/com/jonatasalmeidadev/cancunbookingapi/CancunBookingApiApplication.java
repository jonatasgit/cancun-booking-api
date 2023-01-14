package com.jonatasalmeidadev.cancunbookingapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class CancunBookingApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CancunBookingApiApplication.class, args);
	}

}
