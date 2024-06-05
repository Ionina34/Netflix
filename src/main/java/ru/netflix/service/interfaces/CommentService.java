package ru.netflix.service.interfaces;

import java.util.List;

import ru.netflix.model.Comment;

public interface CommentService {
	List<Comment> findCommentByFilmsId(Long filmId);
}
