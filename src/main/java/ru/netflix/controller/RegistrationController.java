package ru.netflix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import jakarta.validation.Valid;
import ru.netflix.controller.entities.entity.request.UserDto;
import ru.netflix.service.Impl.UserService;
import ru.netflix.service.exeption.UserAlreadyExistException;
import ru.netflix.service.interfaces.IUserService;

@Controller
public class RegistrationController {
	@Autowired
	private IUserService userService;

	@GetMapping("/registration")
	//Метод для отображения страницы регистрации
	public String showRegistrationForm(WebRequest request, Model model) {
		UserDto userDto = new UserDto();
		model.addAttribute("user", userDto);
		return "auth/registration";
	}

	@PostMapping("/registration")
	/** Метотд для регистрации 
	 * @param userDto - введенная пользователем информации*/
	public String registerUserAccount(@ModelAttribute("user") @Valid UserDto userDto, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("user",userDto);
			model.addAttribute("errors", bindingResult.getAllErrors().stream()
					.map(ObjectError::getDefaultMessage)
                    .toList());
			return "auth/registration";
		} else
			try {
				userService.registerNewUserAccount(userDto);
			} catch (UserAlreadyExistException uaeEx) {
				model.addAttribute("message", "На эту почту уже зарегистрирован аккаунт");
				return "auth/registration";
			}

		return "redirect:/films";
	}
}
