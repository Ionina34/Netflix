package ru.netflix.controller.entities.entity.request;

import java.util.List;

import lombok.Data;
import ru.netflix.model.Film;
import ru.netflix.model.Screenwriter;

@Data
/** Класс для представления данных, необходимых для обновления сценриста
 * Содержит информацию об id сценариста, новые данные и его фильмы*/
public class RequestToUpdateTheScreenwriter {
	/**
     * Id  сценариста, который обновляется.
     */
	Long screenwriterId;
	
	/**
     * Новая инф-ция об сценаристе.
     */
	Screenwriter screenwriter;
	
	/**
     * Список его фильмов.
     */
	List<Film> films;
}
