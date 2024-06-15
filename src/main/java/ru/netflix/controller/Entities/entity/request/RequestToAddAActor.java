package ru.netflix.controller.entities.entity.request;

import java.util.List;

import lombok.Data;
import ru.netflix.model.Actor;
import ru.netflix.model.Film;

@Data
/** Класс для представления данных, необходимых для добавления актера
 * Содержит информацию об актере и список фильмов в которых он снимался*/
public class RequestToAddAActor {
	/**
     * Актер, коорый добавляется.
     */
	Actor actor;
	
	/**
     * Список его фильмов.
     */
	List<Film> films;
}
