package ru.netflix.service.interfaces;

import java.util.List;

import ru.netflix.model.Film;
import ru.netflix.model.Genre;

public interface GenreService {
	List<Genre>getAll();
	List<Genre> getRandomGenres();
	List<Genre> findGenresByFilmsId(Long filmId);
	Genre findByName(String name);
	
	void updateFilmGenres(List<Genre> genresUpdate,Film film);
	void addFilmGenres(List<Genre> genres,Film film);
}
