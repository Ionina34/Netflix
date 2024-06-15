package ru.netflix.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
	@GetMapping("/login")
	//Метод для отображения страницы входа
	String login() {
		return "auth/login";
	}
}
