package ru.netflix.controller.entities.entity.request;

import java.util.List;

import lombok.Data;
import ru.netflix.model.*;

@Data
/** Класс для представления данных, необходимых для обновления фильма
 * Содержит информацию об id фильма, новые данные и инф-ция об стаффе*/
public class RequestToUpdateTheMovie {
	/** Id фильма, который обновляется */
	Long filmId;
	
	/**  Новая инф-ция об фильме */
	Film  film;
	
	/** Список его жанром */
	List<Genre> genres;
	
	/**  Список его стран */
	List<Country> countries;
	
	/** Список его актеров */
	List<Actor> actors;
	
	/** Список его режиссеров */
	List<Director> directors;
	
	/** Список его сценаристов */
	List<Screenwriter> screenwriters;
}
