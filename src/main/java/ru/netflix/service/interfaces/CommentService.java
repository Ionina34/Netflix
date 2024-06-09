package ru.netflix.service.interfaces;

import java.security.Principal;
import java.util.List;

import ru.netflix.model.Comment;

public interface CommentService {
	List<Comment> findCommentByFilmsId(Long filmId);
	
	Comment saveComment(String comment,Long filmId,Principal principal);
}
