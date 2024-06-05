package ru.netflix.service.interfaces;

import java.util.List;

import org.springframework.data.domain.*;

import ru.netflix.model.Film;

public interface FilmService {
	Film findByName(String name);
	List<Film> findAllFilms();
	Page<Film> findAllFilms(Pageable pageable);
	Page<Film> findFilmsByUsersId(Long userId,Pageable pageable);
	Page<Film> findFilmsByGenresId(Long genreId,Pageable pageable);
	Page<Film> findFilmsByCountryId(Long countryId,Pageable pageable);
	Page<Film> findByFilterContainingIgnoreCase(String filter,Pageable pageable);
	
	List<Film> findRandomFilms();
	List<Film> getTop10FilmsByAverageRating();
	
	Film getFilmById(Long id);
	List<Film> getFilmsByGenreId(Long genreId);
	List<Film> getFilmsByActorId(Long actorId);
	List<Film> getFilmsByDirectorId(Long directorId);
	List<Film> getFilmsByScreenwriterId(Long screenwriterId);
	List<Film> getFilmsByUsersId(Long userId);
	List<Film>getFavFilmByUserIdAndFilmId(Long userId,Long filmsId);
	
	void saveFilm(Film film);
	void updateFilm(Long id,Film updateFilm);
	void delete(Long filmId);
}
