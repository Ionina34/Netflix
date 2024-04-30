package ru.netflix.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;

@Configuration
public class AppConfiguration {
	@Bean
	public LayoutDialect layoutDialect() {
		return new LayoutDialect();
	}
}
