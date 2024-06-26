package ru.netflix.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import ru.netflix.service.interfaces.ActorService;
import ru.netflix.service.interfaces.FilmService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/actor")
public class ActorController {
	private final ActorService actorService;
	private final FilmService filmService;
	
	@GetMapping("/{id}")
	/** Метод для отображения инф-ции об актере*/
	public String showActorDetails(@PathVariable("id") Long id,Model model) {
		model.addAttribute("human", actorService.findActorById(id));
		model.addAttribute("films", filmService.getFilmsByActorId(id));
		return "human-details";
	}
}
