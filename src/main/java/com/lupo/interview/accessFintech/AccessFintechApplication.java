package com.lupo.interview.accessFintech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = {"com.lupo.interview.accessFintech"})
public class AccessFintechApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccessFintechApplication.class, args);
	}

}
