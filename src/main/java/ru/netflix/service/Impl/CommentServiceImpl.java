package ru.netflix.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.netflix.model.Comment;
import ru.netflix.repository.CommentRepository;
import ru.netflix.service.interfaces.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentRepository commentRepository;

	@Override
	public List<Comment> findCommentByFilmsId(Long filmId) {
		return commentRepository.findCommentsByFilmId(filmId);
	}

}
