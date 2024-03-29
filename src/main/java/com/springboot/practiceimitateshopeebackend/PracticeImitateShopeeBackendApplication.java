package com.springboot.practiceimitateshopeebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class PracticeImitateShopeeBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PracticeImitateShopeeBackendApplication.class, args);
	}

	//todo select payment methods
	//todo kafka to handle all events
	//todo validation
	//todo UNIT TEST

	//todo flyway practice

	//todo sales history
	//todo separate customers and vendors? make a person entity and use inheritance??
	//todo vendors can generate their own sku code for their product

}
