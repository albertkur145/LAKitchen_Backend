package com.lakitchen.LA.Kitchen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {
		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
public class LaKitchenApplication {

	public static void main(String[] args) {
		SpringApplication.run(LaKitchenApplication.class, args);
	}

}
