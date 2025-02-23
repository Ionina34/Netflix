package ru.netflix.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import ru.netflix.controller.entities.entity.responce.FilmScoreAndComment;
import ru.netflix.controller.entities.entity.responce.FilmViewModel;
import ru.netflix.model.Comment;
import ru.netflix.model.Film;
import ru.netflix.model.Genre;
import ru.netflix.model.Rating;
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
	private final CommentService commentService;
	private final RatingService ratingService;
	private final IUserService userService;
	
	@GetMapping
	//Метод для отображения главной страницы
	public String mainHtml(Model model) {
		List<Film> films= filmService.getTop10FilmsByAverageRating();
		
		model.addAttribute("films", films);
		model.addAttribute("newFilms", filmService.getNewFilms(LocalDate.now().minus(Period.ofYears(1)), LocalDate.now()));
		model.addAttribute("awaitedFilms", filmService.getNewFilms(LocalDate.now(),LocalDate.now().plus(Period.ofYears(1))));
		model.addAttribute("watchingItNnow", filmService.findAllByOrderByViewsAsc());
		model.addAttribute("activePage","main");
		return "main";
	}
	
	@GetMapping("/all")
	//Метод для отображения страницы со всеми фильмами
	public String findAllFilm(Model model){
		model.addAttribute("activePage","films");
		model.addAttribute("genres", genreService.getAll());
		model.addAttribute("countries", countryService.getAll());
		return "films";
	}

	@GetMapping("/{id}")
	/** Метод для отображения информации о фильма
	 * @param id - id фильма*/
	public String showFilmDetails(@PathVariable("id") Long id,Model model,Principal principal) {
		
		FilmViewModel viewModel = new FilmViewModel(filmService.getFilmById(id), genreService.findGenresByFilmsId(id),
				countryService.findCountriesByFilmsId(id), actorService.findActorsByFilmsId(id),
				directorService.findDirectorsByFilmsId(id), screenwriterService.findScreenwritersByFilmsId(id));
		filmService.actionWithTheFilm(id);
		model.addAttribute("ModelFilm", viewModel);
		model.addAttribute("comments", getFilmComments(id));
		
		//Если пользователь аунтифицирован
		if(principal!=null) {
			//Оценка фильма
			List<Rating> rating=ratingService.getRatingByUserIdAndFilmId(
					userService.findByEmail(principal.getName()).get().getId(),
					id);
			//Оценил ли
			model.addAttribute("didTheUserRateIt", !rating.isEmpty());
		}
		return "film-details";
	}
	
	@GetMapping("/fav")
	//Метод для отображения страницы избранных
	public String showFavouritesFilms(Model model,Principal principal) {
		model.addAttribute("activePage","fav");
		return "favourites";
	}
	
	/** Метлд для получения комментариев
	 * @param filmId*/
	private List<FilmScoreAndComment> getFilmComments(Long filmId){
		List<FilmScoreAndComment> comments=new ArrayList<FilmScoreAndComment>();
		
		for(Comment comment:commentService.findCommentByFilmsId(filmId)) {
			List<Rating> rating=ratingService.getRatingByUserIdAndFilmId(comment.getUser().getId(), filmId);
			comments.add(new FilmScoreAndComment(comment
					,!rating.isEmpty() ? rating.get(0).getValue() :0));
		}
		return comments;
	}
	
	
}
