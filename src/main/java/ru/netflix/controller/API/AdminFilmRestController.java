package ru.netflix.controller.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ru.netflix.controller.entities.entity.request.RequestToAddAMovie;
import ru.netflix.controller.entities.entity.request.RequestToUpdateTheMovie;
import ru.netflix.model.Film;
import ru.netflix.service.interfaces.*;

@RestController
public class AdminFilmRestController {
	@Autowired
	private FilmService  filmService;
	
	@Autowired
	private GenreService genreService;
	
	@Autowired
	private CountryService countryService;
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private DirectorService directorService;
	
	@Autowired
	private ScreenwriterService screenwriterService;
	
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
		directorService.updateFilmDirectors(data.getDirectors(), film);
		screenwriterService.updateFilmScreenwriters(data.getScreenwriters(), film);
		
		return ResponseEntity.ok(film);
	}
	
	@PostMapping("/admin/films/add")
	public ResponseEntity<Film> addFilm(@RequestBody RequestToAddAMovie data){
		filmService.saveFilm(data.getFilm());
		
		Film film = filmService.findByName(data.getFilm().getName());
		genreService.addFilmGenres(data.getGenres(), film);
		countryService.addFilmCountries(data.getCountries(), film);
		actorService.addFilmActors(data.getActors(), film);
		directorService.addFilmDirectors(data.getDirectors(), film);
		screenwriterService.addFilmScreenwriters(data.getScreenwriters(), film);
		
		return ResponseEntity.ok(data.getFilm());
	}
	
	@DeleteMapping("/admin/films/delete/{id}")
	public ResponseEntity<Long> deleteFilm(@PathVariable Long id) {
	    filmService.delete(id);
	    
	    return  ResponseEntity.ok(id);
	}
}
