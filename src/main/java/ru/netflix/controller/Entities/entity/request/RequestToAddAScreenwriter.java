package ru.netflix.controller.entities.entity.request;

import java.util.List;

import lombok.Data;
import ru.netflix.model.Film;
import ru.netflix.model.Screenwriter;

@Data
/** Класс для представления данных, необходимых для добавления сценариста
 * Содержит информацию об сценаристе и список его фильмов*/
public class RequestToAddAScreenwriter {
	/**
     * Сценарист, коорый добавляется.
     */
	Screenwriter screenwriter;
	
	/**
     * Список его фильмов.
     */
	List<Film> films;
}
