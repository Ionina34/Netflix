package ru.netflix.service.interfaces;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.*;
import org.springframework.data.repository.query.Param;

import ru.netflix.model.Film;

public interface FilmService {
	Film findByName(String name);
	List<Film> findAllFilms();
	Page<Film> findAllFilms(Pageable pageable);
	Page<Film> findFilmsByUsersId(Long userId,Pageable pageable);
	Page<Film> findFilmsByGenresId(Long genreId,Pageable pageable);
	Page<Film> findFilmsByCountryId(Long countryId,Pageable pageable);
	Page<Film> findByFilterContainingIgnoreCase(String filter,Pageable pageable);
	Page<Film> getFilmsOrderByNameAndReleaseDate(String orderData, String orderName, Pageable pageable);
	Page<Film> findByGenresNameAndCountriesNameOrderByReleaseDateOrderByName(String genreName,
			String countryName, String orderData, String orderName, Pageable pageable);
	
	Film findRandomFilm();
	List<Film> getTop10FilmsByAverageRating();
	List<Film> findAllByOrderByViewsAsc();
	List<Film> getNewFilms(LocalDate startDate,LocalDate endDate);
	
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
	
	void actionWithTheFilm(Long filmId);
}
