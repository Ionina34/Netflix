package ru.netflix.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import ru.netflix.model.Film;
import ru.netflix.service.interfaces.ActorService;
import ru.netflix.service.interfaces.DirectorService;
import ru.netflix.service.interfaces.FilmService;
import ru.netflix.service.interfaces.ScreenwriterService;

@Controller	
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminStaffController {
	private final FilmService filmService;
	private final ActorService actorService;
	private final DirectorService directorService;
	private final ScreenwriterService screenwriterService;

	@GetMapping("/actors")
	public String showAdminPanelActors(Model model) {
		model.addAttribute("activePage","actors");
		return "admin/actors/actors";
	}
	
	@GetMapping("/actors/update/{id}")
	public String updateActorView(Model model,@PathVariable("id") Long id) {
		model.addAttribute("actor", actorService.findActorById(id));
		List<Film> filmsActor= filmService.getFilmsByActorId(id);
		model.addAttribute("filmsActor",filmsActor);
		model.addAttribute("films", deleteFilmInList(filmService.findAllFilms(), filmsActor));
		
		return "admin/actors/updateActor";
	}
	
	@GetMapping("/actors/add")
	public String addActorView(Model model) {
		model.addAttribute("films",filmService.findAllFilms());
		
		return "admin/actors/addActor";
	}
	
	@GetMapping("/directors")
	public String showAdminPanelDirectors(Model model) {
		model.addAttribute("activePage","directors");
		return "admin/directors/directors";
	}
	
	@GetMapping("/directors/update/{id}")
	public String updateDirectorView(Model model,@PathVariable("id") Long id) {
		model.addAttribute("director", directorService.findDirectorById(id));
		List<Film> filmsDirector= filmService.getFilmsByDirectorId(id);
		model.addAttribute("filmsDirector",filmsDirector);
		model.addAttribute("films", deleteFilmInList(filmService.findAllFilms(), filmsDirector));
		
		return "admin/directors/updateDirector";
	}
	
	@GetMapping("/directors/add")
	public String addDirectorView(Model model) {
		model.addAttribute("films",filmService.findAllFilms());
		
		return "admin/directors/addDirector";
	}
	
	@GetMapping("/screenwriters")
	public String showAdminPanelScreenwriters(Model model) {
		model.addAttribute("activePage","screenwriters");
		return "admin/screenwriters/screenwriters";
	}
	
	@GetMapping("/screenwriters/update/{id}")
	public String updateScreenwritersView(Model model,@PathVariable("id") Long id) {
		model.addAttribute("screenwriter", screenwriterService.findScreenwriterById(id));
		List<Film> filmsScreenwriters= filmService.getFilmsByScreenwriterId(id);
		model.addAttribute("filmsScreenwriters",filmsScreenwriters);
		model.addAttribute("films", deleteFilmInList(filmService.findAllFilms(), filmsScreenwriters));
		
		return "admin/screenwriters/updateScreenwriter";
	}
	
	@GetMapping("/screenwriters/add")
	public String addScreenwritersView(Model model) {
		model.addAttribute("films",filmService.findAllFilms());
		
		return "admin/screenwriters/addScreenwriter";
	}
	
	private List<Film> deleteFilmInList(List<Film> films,List<Film> filmsActor){
		return films.stream().filter(x->!filmsActor.contains(x)).toList();
	}
}
