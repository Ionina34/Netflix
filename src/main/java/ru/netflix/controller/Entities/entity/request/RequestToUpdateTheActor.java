package ru.netflix.controller.entities.entity.request;

import java.util.List;

import lombok.Data;
import ru.netflix.model.Actor;
import ru.netflix.model.Film;

@Data
/** Класс для представления данных, необходимых для обновления актера
 * Содержит информацию об id актера, новые данные и фильмы в которых он снимался*/
public class RequestToUpdateTheActor {
	/**
     * Id  актера, который обновляется.
     */
	Long actorId;
	
	/**
     * Новая инф-ция об актере.
     */
	Actor actor;
	
	/**
     * Список его фильмов.
     */
	List<Film> films;
}
