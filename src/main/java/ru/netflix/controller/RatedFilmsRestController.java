package ru.netflix.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.netflix.model.Film;
import ru.netflix.service.interfaces.IUserService;
import ru.netflix.service.interfaces.RatingService;

@RestController
public class RatedFilmsRestController {
	@Autowired
	private RatingService ratingService;
	
	@Autowired
	private IUserService userService;

	@GetMapping("/user/films/rated")
	public List<Film> getRatedFilmsBuUser(Principal principal) {
		return ratingService.getRatedFilmsByUserId(userService.findByEmail(principal.getName()).get().getId());
	}
	
}
