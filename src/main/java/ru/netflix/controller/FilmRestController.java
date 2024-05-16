package ru.netflix.controller;

import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.netflix.model.Film;
import ru.netflix.service.interfaces.CountryService;
import ru.netflix.service.interfaces.FilmService;
import ru.netflix.service.interfaces.GenreService;

@RestController
@RequiredArgsConstructor
public class FilmRestController {
	private final FilmService filmService;
	private final GenreService genreService;
	private final CountryService countryService;

	@GetMapping("/films/all/get")
	public Page<Film> getAllFilms(Pageable pageable) {
		/*
		 * ServiceResponce<List<Film>> response=new
		 * ServiceResponce<>("success",filmService.findAllFilms()); return new
		 * ResponseEntity<Object>(response, HttpStatus.OK);
		 */

		return filmService.findAllFilms(pageable);
	}

	@GetMapping("/films/search")
	public Page<Film> getFilterFilms(Pageable pageable, @RequestParam(name = "filter") String filter) {
		return filmService.findByFilterContainingIgnoreCase(filter, pageable);
	}
	
	@GetMapping("/films/search/genre")
	public Page<Film> getFilmsByGenres(Pageable pageable,@RequestParam(name="genre") String genre){
		genreService.findByName(genre);
		return filmService.findFilmsByGenresId(genreService.findByName(genre).getId(),pageable);
	}
	
	@GetMapping("/films/search/country")
	public Page<Film> getFilmsByCountries(Pageable pageable,@RequestParam(name="country") String country){
		countryService.findByName(country);
		return filmService.findFilmsByCountryId(countryService.findByName(country).getId(),pageable);
	}
}
