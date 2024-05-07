package ru.netflix.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ru.netflix.controller.entities.RequestFilmFav;
import ru.netflix.model.Film;
import ru.netflix.model.User;
import ru.netflix.service.interfaces.FilmService;
import ru.netflix.service.interfaces.IUserService;

@RestController
public class FavouriteFilmController {
	@Autowired
	private IUserService userService;

	@Autowired
	private FilmService filmService;

	@PostMapping("/user/films/add")
	public ResponseEntity<Film> addFilm(@RequestBody RequestFilmFav getFilm, Principal principal) {
		Film favFilm = new Film();
		Film film = filmService.getFilmById(getFilm.getFilmId());
		User user = userService.findByEmail(principal.getName()).get();

		if (filmService.getFavFilmByUserIdAndFilmId(user.getId(), film.getId()).size() > 0) {
			user.removeFilm(film.getId());
			userService.save(user);
		} else {
			user.addFilm(film);
			userService.save(user);
		}
		return new ResponseEntity<Film>(favFilm, HttpStatus.OK);

	}
}
