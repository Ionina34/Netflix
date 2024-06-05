package ru.netflix.controller.entities.entity.responce;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.netflix.model.Comment;

@Data
@AllArgsConstructor
public class FilmScoreAndComment {
	private Comment comment;
	private int rating;
}
