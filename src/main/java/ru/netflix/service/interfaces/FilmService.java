package ru.netflix.service.interfaces;

import java.util.List;

import org.springframework.data.domain.*;

import ru.netflix.model.Film;

public interface FilmService {
	Page<Film> findAllFilms(Pageable pageable);
	List<Film> findAllFilms();
	Page<Film> findByFilterContainingIgnoreCase(String filter,Pageable pageable);
	List<Film> findRandomFilms();
	
	Film getFilmById(Long id);
	List<Film> getFilmsByGenreId(Long genreId);
	List<Film> getFilmsByActorId(Long actorId);
	
	void saveFilm(Film film);
	void updateFilm(Long id,Film updateFilm);
	void deleteFilm(Long id);
}
