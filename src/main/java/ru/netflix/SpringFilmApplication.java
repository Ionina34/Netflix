package ru.netflix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication()
@EnableCaching
public class SpringFilmApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringFilmApplication.class,args);

	}

}
