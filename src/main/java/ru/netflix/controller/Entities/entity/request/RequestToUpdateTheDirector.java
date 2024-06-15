package ru.netflix.controller.entities.entity.request;

import java.util.List;

import lombok.Data;
import ru.netflix.model.Director;
import ru.netflix.model.Film;

@Data
/** Класс для представления данных, необходимых для обновления режиссера
 * Содержит информацию об id режиссера, новые данные и фильмы в которых он снимался*/
public class RequestToUpdateTheDirector {
	/**
     * Id режиссера, который обновляется.
     */
	Long directorId;
	
	/**
     * Новая инф-ция об режиссере.
     */
	Director  director;
	
	/**
     * Список его фильмов.
     */
	List<Film> films;
}
