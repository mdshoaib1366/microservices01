package com.nit.mybank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MybankApplication {

	public static void main(String[] args) {
		SpringApplication.run(MybankApplication.class, args);
	}

}
