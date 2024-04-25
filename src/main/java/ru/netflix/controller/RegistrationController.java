package ru.netflix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import ru.netflix.controller.entities.UserDto;
import ru.netflix.service.Impl.UserService;
import ru.netflix.service.exeption.UserAlreadyExistException;

@Controller
public class RegistrationController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/registration")
	public String showRegistrationForm(WebRequest request, Model model) {
		UserDto userDto = new UserDto();
		model.addAttribute("user", userDto);
		return "auth/registration";
	}

	@PostMapping("/registration")
	public String registerUserAccount(@ModelAttribute("user") @Valid UserDto userDto, 
			HttpServletRequest request,
			Model model,
			Errors errors) {
		try {
			userService.registerNewUserAccount(userDto);
		} catch (UserAlreadyExistException uaeEx) {
			model.addAttribute("message", "An account for that username/email already exists.");
			return "auth/registration";
		}
		return "redirect:/films";
	}
}
