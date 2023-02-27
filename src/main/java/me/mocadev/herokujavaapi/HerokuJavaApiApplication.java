package me.mocadev.herokujavaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class HerokuJavaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(HerokuJavaApiApplication.class, args);
	}

}
