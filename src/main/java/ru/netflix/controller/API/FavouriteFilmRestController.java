package ru.netflix.controller.API;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.netflix.controller.entities.entity.request.RequestFilmFav;
import ru.netflix.model.Film;
import ru.netflix.model.User;
import ru.netflix.service.interfaces.FilmService;
import ru.netflix.service.interfaces.IUserService;

@RestController
public class FavouriteFilmRestController {
	@Autowired
	private IUserService userService;

	@Autowired
	private FilmService filmService;
	
	/* Получет список всех избранных фильмов пользователя для дальнейшей проверки */
	@GetMapping("/user/films")
	public List<Film> findFilmByUserId(Principal principal) {
		return filmService.getFilmsByUsersId(userService.findByEmail(principal.getName()).get().getId());
	}

	//Получает избранные фильмы пользователя по-странично
	@GetMapping("/user/films/fav")
	public Page<Film> getAllFilms(Pageable pageable, Principal principal) {
		return filmService.findFilmsByUsersId(userService.findByEmail(principal.getName()).get().getId(), pageable);
	}
	
	//Для проверки есть фильм в избранном
	@GetMapping("/user/film/fav")
	public boolean searchForAMovieInFav(@RequestParam(name="filmId") Long filmId,Principal principal) {
		return filmService.getFavFilmByUserIdAndFilmId(userService.findByEmail
				(principal.getName()).get().getId(), filmId).size() > 0;
	}

	/*
	 * Метод для добаления фильма к списку избранных, в этом же методе происходит
	 * удаление, если фильм уже есть в этом списке
	 */
	@PostMapping("/user/films/add")
	public ResponseEntity<Film> addFilm(@RequestBody RequestFilmFav film, Principal principal) {
		Film favFilm = new Film();
		User user = userService.findByEmail(principal.getName()).get();
		
		if(film.getFilm().getName()=="") {
			film.setFilm(filmService.getFilmById(film.getFilm().getId()));
		}

		if (filmService.getFavFilmByUserIdAndFilmId(user.getId(), film.getFilm().getId()).size() > 0) {
			user.removeFilm(film.getFilm().getId());
			userService.save(user);
		} else {
			user.addFilm(film.getFilm());
			userService.save(user);
		}
		return new ResponseEntity<Film>(favFilm, HttpStatus.OK);

	}
	
	/*
	 * Метод удаления, выделлыннйм в отдельный метод для удаления из списка на
	 * прямую без проверок со старницы избранных фильмов
	 */
	@PostMapping("/user/films/remove")
	public ResponseEntity<Film> removeFilm(@RequestBody RequestFilmFav film, Principal principal) {
		User user= userService.findByEmail(principal.getName()).get();
		user.removeFilm(film.getFilm().getId());
		userService.save(user);

		return new ResponseEntity<Film>(film.getFilm(), HttpStatus.OK);

	}
}
