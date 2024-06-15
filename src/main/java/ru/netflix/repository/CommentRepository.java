package ru.netflix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.netflix.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
	/** Получение комментарии фильма */
	List<Comment> findCommentsByFilmId(Long filmId);
}
