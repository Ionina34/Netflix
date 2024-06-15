package ru.netflix.controller.API;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ru.netflix.controller.entities.entity.request.RequestCommentOnTheFilm;
import ru.netflix.controller.entities.entity.responce.FilmScoreAndComment;
import ru.netflix.model.Comment;
import ru.netflix.service.interfaces.CommentService;
import ru.netflix.service.interfaces.FilmService;
import ru.netflix.service.interfaces.RatingService;

@RestController
/** Контроллер для работы с комментариями */
public class CommentRestController {
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private FilmService filmService;
	
	@Autowired 
	private RatingService  ratingService;
	
	@PostMapping("/user/film/comment")
	/** Метод для добавления комментария к фильму
	 * @param data - данные для добавления
	 * (id фильма, комметрий, оценка)*/
	public ResponseEntity<FilmScoreAndComment> commentOnTheFilm(@RequestBody RequestCommentOnTheFilm data,Principal principal){
		Comment comment= commentService.saveComment(data.getComment(), data.getFilmId(), principal);
		filmService.actionWithTheFilm(data.getFilmId());
		if(data.getRating()!=0)
			ratingService.save(data.getFilmId(),data.getRating(), principal);
		
		return ResponseEntity.ok(new FilmScoreAndComment(comment, data.getRating()));
	}
}
