package ru.netflix.service.Impl;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.netflix.model.Comment;
import ru.netflix.model.Film;
import ru.netflix.model.User;
import ru.netflix.repository.CommentRepository;
import ru.netflix.repository.FilmRepository;
import ru.netflix.repository.UserRepository;
import ru.netflix.service.interfaces.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private FilmRepository filmRepository;

	@Override
	public List<Comment> findCommentByFilmsId(Long filmId) {
		return commentRepository.findCommentsByFilmId(filmId);
	}

	@Override
	public Comment saveComment(String com, Long filmId,Principal principal) {
		User user=userRepository.findByEmail(principal.getName()).get();
		Film film= filmRepository.findById(filmId).get();
		
		Comment comment=new Comment();
		comment.setComment(com);
		comment.setUser(user);
		comment.setFilm(film);
		comment.setCreated_at(LocalDate.now());
		
		return commentRepository.save(comment);
	}

}
