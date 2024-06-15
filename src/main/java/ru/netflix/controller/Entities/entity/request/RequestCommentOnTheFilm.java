package ru.netflix.controller.entities.entity.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
/** Класс для представления данных, необходимых для комментирования фильма
 * Содержит информацию о id фильма, комментарий  и его оценке */
public class RequestCommentOnTheFilm {
	
	/**
     * Id фильма, который комментрируется.
     */
	Long filmId;
	
	/**
     * Комментарий фильма.
     */
	String comment;
	
	/**
     * Оценка фильма.
     */
	int rating;
}
