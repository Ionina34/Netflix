package ru.netflix.service.interfaces;

import java.util.List;

import ru.netflix.model.Genre;

public interface GenreService {
	List<Genre> getRandomGenres();
	List<Genre> findGenresByFilmsId(Long filmId);
}
