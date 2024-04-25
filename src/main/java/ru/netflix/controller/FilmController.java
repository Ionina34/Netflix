package ru.netflix.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import ru.netflix.controller.entities.*;
import ru.netflix.model.Film;
import ru.netflix.model.Genre;
import ru.netflix.service.interfaces.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {
	private final FilmService filmService;
	private final GenreService genreService;
	private final CountryService countryService;
	private final ActorService actorService;
	private final DirectorService directorService;
	private final ScreenwriterService screenwriterService;
	
	@GetMapping
	public String mainHtml(Model model) {
		List<Film> films= filmService.findRandomFilms();
		ArrayList<Genre> genres=new ArrayList<>(genreService.getRandomGenres());
		
		List<Film> genreOne=filmService.getFilmsByGenreId(genres.get(0).getId());
		List<Film> genreTwo=filmService.getFilmsByGenreId(genres.get(1).getId());
		List<Film> genreThree=filmService.getFilmsByGenreId(genres.get(2).getId());
		
		model.addAttribute("films", films);
		model.addAttribute("genres", genres);		
		model.addAttribute("filmsByGenreOne", genreOne);
		model.addAttribute("filmsByGenreTwo", genreTwo);
		model.addAttribute("filmsByGenreTree", genreThree);
		model.addAttribute("activePage","main");
		return "main";
	}
	
	@GetMapping("/all")
	public String findAllFilm(Model model){
		List<Film> films= filmService.findAllFilms();
		model.addAttribute("films", films);
		model.addAttribute("activePage","films");
		return "films";
	}

	@GetMapping("/{id}")
	public String showFilmDetails(@PathVariable("id") Long id,Model model) {
		FilmViewModel viewModel = new FilmViewModel(filmService.getFilmById(id), genreService.findGenresByFilmsId(id),
				countryService.findCountriesByFilmsId(id), actorService.findActorsByFilmsId(id),
				directorService.findDirectorsByFilmsId(id), screenwriterService.findScreenwritersByFilmsId(id));
		model.addAttribute("ModelFilm", viewModel);
		return "film-details";
	}
}
