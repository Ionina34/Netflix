package ru.netflix.controller.entities.entity.request;

import lombok.Data;
import ru.netflix.model.Film;

@Data
/** Класс для представления данных, необходимых для добавления и удаления фильма
 * из избранных
 * Содержит информацию о фильме */
public class RequestFilmFav {
	/**
     * Фильм, который пользователь добавляет в или удаляет из избранных.
     */
	private Film film;
}
