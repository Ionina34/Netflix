package ru.netflix.controller;

import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.netflix.model.Film;
import ru.netflix.service.interfaces.FilmService;

@RestController
@RequiredArgsConstructor
public class FilmRestController {
	private final FilmService filmService;

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
}
