package com.example.bookweb_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@SpringBootApplication
public class BookwebManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookwebManagementApplication.class, args);
	}

}
