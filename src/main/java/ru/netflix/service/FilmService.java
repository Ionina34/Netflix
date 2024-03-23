package ru.netflix.service;

import java.util.List;

import ru.netflix.model.Actor;
import ru.netflix.model.Film;
import ru.netflix.model.Genre;

public interface FilmService {
	List<Film> findAllFilms();
	List<Film> findRandomFilms();
	
	Film getFilmById(Long id);
	List<Film> getFilmsByGenreId(Long genreId);
	
	void saveFilm(Film film);
	void updateFilm(Long id,Film updateFilm);
	void deleteFilm(Long id);
}
