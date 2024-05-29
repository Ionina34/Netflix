package ru.netflix.controller.entities.entity.request;

import java.util.List;

import lombok.Data;
import ru.netflix.model.*;

@Data
public class RequestToUpdateTheMovie {
	Long filmId;
	Film  film;
	List<Genre> genres;
	List<Country> countries;
	List<Actor> actors;
}
