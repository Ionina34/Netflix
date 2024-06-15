
package ru.netflix.service.Impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ru.netflix.model.Film;
import ru.netflix.repository.FilmRepository;
import ru.netflix.service.interfaces.FilmService;

@Service
public class FilmServiceImpl implements FilmService {

	@Autowired
	private FilmRepository repository;
	
	@Override
	public Film findByName(String name) {
		return repository.findByName(name);
	}
	
	@Override
	public List<Film> findAllFilms() {
		return repository.findAll();
	}

	@Override
	public Page<Film> findAllFilms(Pageable pageable) {
		return repository.findAll(pageable);
	}
	
	@Override
	public Page<Film> findFilmsByUsersId(Long userId,Pageable pageable){
		return repository.findFilmsByUsersId(userId, pageable);
	}
	
	@Override
	public Page<Film> findFilmsByGenresId(Long genreId,Pageable pageable) {
		return repository.findFilmsByGenresId(genreId,pageable);
	}
	
	@Override
	public Page<Film> findFilmsByCountryId(Long countryId,Pageable pageable) {
		return repository.findFilmsByCountriesId(countryId,pageable);
	}


	@Override
	public Page<Film> findByFilterContainingIgnoreCase(String filter, Pageable pageable) {
		return repository.findByNameContainingIgnoreCase(filter, pageable);
	}
	
	@Override
	public Page<Film> getFilmsOrderByNameAndReleaseDate(String orderData, String orderName, Pageable pageable) {
		return repository.getFilmsOrderByNameAndReleaseDate(orderData, orderName, pageable);
	}

	@Override
	public Page<Film> findByGenresNameAndCountriesNameOrderByReleaseDateOrderByName(String genreName,
			String countryName, String orderData, String orderName, Pageable pageable) {
		return repository.findByGenresNameAndCountriesNameOrderByReleaseDateOrderByName(genreName, countryName, orderData, orderName, pageable);
	}

	@Override
	@Cacheable("film-random")
	public Film findRandomFilm() {
		return repository.getRandomValues();
	}
	
	@Override
	public List<Film> getTop10FilmsByAverageRating(){
	    return repository.findTop10ByAverageRating();
	}
	
	@Override
	public List<Film> findAllByOrderByViewsAsc(){
		return repository.findAllByOrderByViewsAsc();
	}
	
	@Override 
	public List<Film> getNewFilms(LocalDate startDate,LocalDate endDate){
		return repository.getNewFilms(startDate, endDate);
	}

	@Override
	public Film getFilmById(Long id) {
		return repository.findById(id).orElse(null);
	}
	
	@Override
	public List<Film> getFilmsByGenreId(Long genreId) {
		return repository.findFilmsByGenresId(genreId);
	}

	@Override
	public List<Film> getFilmsByActorId(Long actorId) {
		return repository.findFilmsByActorsId(actorId);
	}
	
	@Override
	public List<Film> getFilmsByDirectorId(Long directorId) {
		return repository.findFilmsByDirectorsId(directorId);
	}
	
	@Override
	public List<Film> getFilmsByScreenwriterId(Long screenwriterId) {
		return repository.findFilmsByScreenwritersId(screenwriterId);
	}
	
	@Override
	public List<Film> getFilmsByUsersId(Long userId){
		return repository.findFilmsByUsersId(userId);
	}

	@Override
	public List<Film> getFavFilmByUserIdAndFilmId(Long userId, Long filmId) {
		return repository.getFavFilmByUserIdAndFilmId(userId, filmId);
	}

	@Override
	public void saveFilm(Film film) {
		film.addFilm(film);
		repository.save(film);
	}

	@Override
	public void updateFilm(Long id, Film updateFilm) {
		Film film = repository.findById(id).orElse(null);
		if (film != null) {
			film.update(updateFilm);

			repository.save(film);
		}
	}

	@Override
	public void delete(Long filmId) {
		repository.deleteById(filmId);
	}
	
	@Override
	public void actionWithTheFilm(Long filmId) {
		Film film=repository.findById(filmId).get();
		film.setViews(film.getViews()+1);
		repository.save(film);
	}
}
