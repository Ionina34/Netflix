package ru.netflix.controller.entities.entity.request;

import lombok.Data;
import ru.netflix.model.Film;

@Data
public class RequestBodyTheFilmIsForEvaluation {
	private Film film;
	private int rating;
}