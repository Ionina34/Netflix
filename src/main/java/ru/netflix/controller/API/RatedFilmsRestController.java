package ru.netflix.controller.API;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ru.netflix.controller.entities.entity.request.RequestBodyTheFilmIsForEvaluation;
import ru.netflix.model.Film;
import ru.netflix.model.Rating;
import ru.netflix.service.interfaces.IUserService;
import ru.netflix.service.interfaces.RatingService;

@RestController
/** Контроллер для оценки фильма пользователем*/
public class RatedFilmsRestController {
	@Autowired
	private RatingService ratingService;

	@Autowired
	private IUserService userService;

	@GetMapping("/user/films/rated")
	/** Метод для получения оценненых фильмов пользователя*/
	public List<Film> getRatedFilmsBuUser(Principal principal) {
		return userService.getRatedFilmsByUserId(userService.findByEmail(principal.getName()).get().getId());
	}
	
	@PostMapping("/user/film/rating/add")
	/** Метод для выставления оценки фильма пользователем 
	 * @param info - фильм и оценка*/
	public ResponseEntity<Film> addRating(@RequestBody RequestBodyTheFilmIsForEvaluation info, Principal principal) {
		ratingService.save(info.getFilm(),info.getRating(),principal);

		return new ResponseEntity<Film>(info.getFilm(), HttpStatus.OK);
	}
}
