package ru.netflix.controller;

import java.security.Principal;
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
	public String mainHtml(Model model) {
		List<Film> films= filmService.getTop10FilmsByAverageRating();
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
		model.addAttribute("activePage","films");
		model.addAttribute("genres", genreService.getAll());
		model.addAttribute("countries", countryService.getAll());
		return "films";
	}

	@GetMapping("/{id}")
	public String showFilmDetails(@PathVariable("id") Long id,Model model,Principal principal) {

		FilmViewModel viewModel = new FilmViewModel(filmService.getFilmById(id), genreService.findGenresByFilmsId(id),
				countryService.findCountriesByFilmsId(id), actorService.findActorsByFilmsId(id),
				directorService.findDirectorsByFilmsId(id), screenwriterService.findScreenwritersByFilmsId(id));
		model.addAttribute("ModelFilm", viewModel);
		model.addAttribute("comments", getFilmComments(id));
		if(principal!=null) {
			List<Rating> rating=ratingService.getRatingByUserIdAndFilmId(
					userService.findByEmail(principal.getName()).get().getId(),
					id);
			model.addAttribute("didTheUserRateIt", !rating.isEmpty());
		}
		return "film-details";
	}
	
	@GetMapping("/fav")
	public String showFavouritesFilms(Model model,Principal principal) {
		model.addAttribute("activePage","fav");
		return "favourites";
	}
	
	private List<FilmScoreAndComment> getFilmComments(Long filmId){
		List<FilmScoreAndComment> comments=new ArrayList<FilmScoreAndComment>();
		for(Comment comment:commentService.findCommentByFilmsId(filmId)) {
			List<Rating> rating=ratingService.getRatingByUserIdAndFilmId(comment.getUser().getId(), filmId);
			comments.add(new FilmScoreAndComment(comment
					,!rating.isEmpty()?rating.getFirst().getValue():0));
		}
		return comments;
	}
}
