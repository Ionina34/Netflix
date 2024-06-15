package ru.netflix.controller.entities.entity.request;

import lombok.Data;
import ru.netflix.model.Film;

@Data
/** Класс для представления данных, необходимых для оценки фильма
 * Содержит информацию о фильме и его оценке */
public class RequestBodyTheFilmIsForEvaluation {
	/**
     * Фильм, который оценивается.
     */
	private Film film;
	/**
     * Оценка фильма.
     */
	private int rating;
}
