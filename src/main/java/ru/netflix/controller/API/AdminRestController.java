package ru.netflix.controller.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ru.netflix.controller.entities.entity.request.RequestToUpdateTheMovie;
import ru.netflix.model.Film;
import ru.netflix.service.interfaces.ActorService;
import ru.netflix.service.interfaces.CountryService;
import ru.netflix.service.interfaces.FilmService;
import ru.netflix.service.interfaces.GenreService;

@RestController
public class AdminRestController {
	@Autowired
	private FilmService  filmService;
	
	@Autowired
	private GenreService genreService;
	
	@Autowired
	private CountryService countryService;
	
	@Autowired
	private ActorService actorService;
	
	@GetMapping("/admin/films/get")
	public ResponseEntity<Page<Film>> getAllFilms(Pageable pageable){
		return ResponseEntity.ok(filmService.findAllFilms(pageable));
	}
	
	@PutMapping("/admin/films/update")
	public ResponseEntity<Film> updateFilm(@RequestBody RequestToUpdateTheMovie data){
		filmService.updateFilm(data.getFilmId(), data.getFilm());
		Film film = filmService.getFilmById(data.getFilmId());
		genreService.updateFilmGenres(data.getGenres(), film);
		countryService.updateFilmCountries(data.getCountries(), film);
		actorService.updateFilmActors(data.getActors(), film);
		
		return ResponseEntity.ok(film);
	}

}
