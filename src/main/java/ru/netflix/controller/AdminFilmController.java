package ru.netflix.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import ru.netflix.controller.entities.entity.responce.FilmViewModel;
import ru.netflix.model.Actor;
import ru.netflix.model.Country;
import ru.netflix.model.Director;
import ru.netflix.model.Genre;
import ru.netflix.model.Screenwriter;
import ru.netflix.service.interfaces.ActorService;
import ru.netflix.service.interfaces.CountryService;
import ru.netflix.service.interfaces.DirectorService;
import ru.netflix.service.interfaces.FilmService;
import ru.netflix.service.interfaces.GenreService;
import ru.netflix.service.interfaces.ScreenwriterService;

@Controller	
@RequiredArgsConstructor
@RequestMapping("/admin/films")
public class AdminFilmController {
	private final FilmService filmService;
	private final GenreService genreService;
	private final CountryService countryService;
	private final ActorService actorService;
	private final DirectorService directorService;
	private final ScreenwriterService screenwriterService;

	@GetMapping()
	public String showAdminPanel(Model model) {
		model.addAttribute("genres", genreService.getAll());
		model.addAttribute("countries", countryService.getAll());
		model.addAttribute("activePage","films");
		return "admin/films/films";
	}
	
	@GetMapping("/update/{id}")
	public String updateFilmView(Model model,@PathVariable("id") Long id) {
		FilmViewModel viewModel = new FilmViewModel(filmService.getFilmById(id), genreService.findGenresByFilmsId(id),
				countryService.findCountriesByFilmsId(id), actorService.findActorsByFilmsId(id),
				directorService.findDirectorsByFilmsId(id), screenwriterService.findScreenwritersByFilmsId(id));
		model.addAttribute("ModelFilm", viewModel);
		
		model.addAttribute("genres", deleteGenreInList(genreService.getAll(), viewModel.getGenres()));
		model.addAttribute("countries", deleteCountryInList(countryService.getAll(), viewModel.getCountries()));
		model.addAttribute("actors", deleteActorInList(actorService.getAll(), viewModel.getActors()));
		model.addAttribute("directors", deleteDirectorInList(directorService.getAll(), viewModel.getDirectors()));
		model.addAttribute("screenwriters", deleteScreenwriterInList(screenwriterService.getAll(), viewModel.getScreenwriters()));
		return "admin/films/updateFilm";
	}
	
	@GetMapping("/add")
	public String addFilmView(Model model) {
		model.addAttribute("genres",genreService.getAll());
		model.addAttribute("countries",countryService.getAll());
		model.addAttribute("actors", actorService.getAll());
		model.addAttribute("directors", directorService.getAll());
		model.addAttribute("screenwriters", screenwriterService.getAll());
		return "admin/films/addFilm";
	}
	
	private List<Genre> deleteGenreInList(List<Genre> genres,List<Genre> genresFilm){
		return genres.stream().filter(x->!genresFilm.contains(x)).toList();
	}
	
	private List<Country> deleteCountryInList(List<Country> countries,List<Country> countriesFilm){
		return countries.stream().filter(x->!countriesFilm.contains(x)).toList();
	}
	
	private List<Actor> deleteActorInList(List<Actor> actors,List<Actor> actorsFilm){
		return actors.stream().filter(x->!actorsFilm.contains(x)).toList();
	}
	
	private List<Director> deleteDirectorInList(List<Director> directors,List<Director> directorsFilm){
		return directors.stream().filter(x->!directorsFilm.contains(x)).toList();
	}
	
	private List<Screenwriter> deleteScreenwriterInList(List<Screenwriter> screenwriters,List<Screenwriter> screenwritersFilm){
		return screenwriters.stream().filter(x->!screenwritersFilm.contains(x)).toList();
	}
}