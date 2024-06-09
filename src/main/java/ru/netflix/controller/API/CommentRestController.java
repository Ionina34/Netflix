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
import ru.netflix.service.interfaces.RatingService;

@RestController
public class CommentRestController {
	@Autowired
	private CommentService commentService;
	
	@Autowired 
	private RatingService  ratingService;
	
	@PostMapping("/user/film/comment")
	public ResponseEntity<FilmScoreAndComment> commentOnTheFilm(@RequestBody RequestCommentOnTheFilm data,Principal principal){
		Comment comment= commentService.saveComment(data.getComment(), data.getFilmId(), principal);
		if(data.getRating()!=0)
			ratingService.save(data.getFilmId(),data.getRating(), principal);
		
		return ResponseEntity.ok(new FilmScoreAndComment(comment, data.getRating()));
	}
}
