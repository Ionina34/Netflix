package ru.netflix.controller.entities.entity.request;

import java.util.List;

import lombok.Data;
import ru.netflix.model.Director;
import ru.netflix.model.Film;

@Data
/** Класс для представления данных, необходимых для добавления режиссера
 * Содержит информацию об режиссере и список фильмов, которые он режисировал*/
public class RequestToAddADirector {
	/**
     * Режиссер, коорый добавляется.
     */
	Director director;
	
	/**
     * Список его фильмов.
     */
	List<Film> films;
}
