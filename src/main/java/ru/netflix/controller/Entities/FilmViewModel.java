package ru.netflix.controller.entities;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.netflix.model.*;

@Data
@AllArgsConstructor
public class FilmViewModel {
	private Film film;
	private List<Genre> genres;
	private List<Country> countries;
	private List<Actor> actors;
	private List<Director> directors;
	private List<Screenwriter> screenwriters;
}
