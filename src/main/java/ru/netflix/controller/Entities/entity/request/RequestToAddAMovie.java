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
public class RequestToAddAMovie {
	Film film;
	List<Genre> genres;
	List<Country> countries;
	List<Actor> actors;
	List<Director> directors;
	List<Screenwriter> screenwriters;
}
