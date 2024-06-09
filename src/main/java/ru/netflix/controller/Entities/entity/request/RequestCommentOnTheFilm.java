package ru.netflix.controller.entities.entity.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestCommentOnTheFilm {
	Long filmId;
	String comment;
	int rating;
}
