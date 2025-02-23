package ru.netflix.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import ru.netflix.controller.entities.entity.responce.FilmViewModel;
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
	/** Метод для отображения страницы всех фильмов
	 * включая жанры и страны для сортировки*/
	public String showAdminPanel(Model model) {
		model.addAttribute("genres", genreService.getAll());
		model.addAttribute("countries", countryService.getAll());
		model.addAttribute("activePage","films");
		return "admin/films/films";
	}
	
	@GetMapping("/update/{id}")
	/** Метод для отображеняи страницы обновления фильма
	 * @param id - id фильма*/
	public String updateFilmView(Model model,@PathVariable("id") Long id) {
		//Информация о фильме
		FilmViewModel viewModel = new FilmViewModel(filmService.getFilmById(id), genreService.findGenresByFilmsId(id),
				countryService.findCountriesByFilmsId(id), actorService.findActorsByFilmsId(id),
				directorService.findDirectorsByFilmsId(id), screenwriterService.findScreenwritersByFilmsId(id));
		model.addAttribute("ModelFilm", viewModel);
		
		//Информация доступная для обновления
		model.addAttribute("genres", deleteInList(genreService.getAll(), viewModel.getGenres()));
		model.addAttribute("countries", deleteInList(countryService.getAll(), viewModel.getCountries()));
		model.addAttribute("actors", deleteInList(actorService.getAll(), viewModel.getActors()));
		model.addAttribute("directors", deleteInList(directorService.getAll(), viewModel.getDirectors()));
		model.addAttribute("screenwriters", deleteInList(screenwriterService.getAll(), viewModel.getScreenwriters()));
		return "admin/films/updateFilm";
	}
	
	@GetMapping("/add")
	//Метод для отображения страницы добавления фильма
	public String addFilmView(Model model) {
		model.addAttribute("genres",genreService.getAll());
		model.addAttribute("countries",countryService.getAll());
		model.addAttribute("actors", actorService.getAll());
		model.addAttribute("directors", directorService.getAll());
		model.addAttribute("screenwriters", screenwriterService.getAll());
		return "admin/films/addFilm";
	}
	
	/** Метод для удаления стаффа, которые есть у фильма из списка
	 * @param staff - список всего стаффа
	 * @param staffFilm - стафф фильма */
	private static List<?> deleteInList(List<?> staff, List<?> staffFilm){
		return staff.stream().filter(x->!staffFilm.contains(x)).toList();
	}
}
