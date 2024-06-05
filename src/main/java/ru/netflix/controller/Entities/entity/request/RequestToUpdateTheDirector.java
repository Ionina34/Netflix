package ru.netflix.controller.entities.entity.request;

import java.util.List;

import lombok.Data;
import ru.netflix.model.Director;
import ru.netflix.model.Film;

@Data
public class RequestToUpdateTheDirector {
	Long directorId;
	Director  director;
	List<Film> films;
}