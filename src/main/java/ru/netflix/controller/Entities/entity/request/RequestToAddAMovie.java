package ru.netflix.controller.entities.entity.request;

import java.util.List;

import lombok.Data;
import ru.netflix.model.Actor;
import ru.netflix.model.Country;
import ru.netflix.model.Director;
import ru.netflix.model.Film;
import ru.netflix.model.Genre;
import ru.netflix.model.Screenwriter;

@Data
/** Класс для представления данных, необходимых для добавления фильма
 * Содержит информацию об фильме и списоки стаффа*/
public class RequestToAddAMovie {
	/**
     * Фильм, коорый добавляется.
     */
	Film film;
	
	/**
     * Список его жанров.
     */
	List<Genre> genres;
	
	/**
     * Список его стран.
     */
	List<Country> countries;
	
	/**
     * Список его актеров.
     */
	List<Actor> actors;
	
	/**
     * Список его режиссеров.
     */
	List<Director> directors;
	
	/**
     * Список его сценаристов.
     */
	List<Screenwriter> screenwriters;
}
