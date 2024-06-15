package ru.netflix.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.netflix.model.Film;
import ru.netflix.model.Genre;
import ru.netflix.repository.GenreRepository;
import ru.netflix.service.interfaces.GenreService;

@Service
public class GenreServiceImpl implements GenreService {

	@Autowired
	private GenreRepository genreRepository;

	@Override
	public List<Genre> getAll() {
		return genreRepository.findAll();
	}

	@Override
	public List<Genre> getRandomGenres() {
		return genreRepository.getTreeRanfomValues();
	}

	@Override
	public List<Genre> findGenresByFilmsId(Long filmId) {
		return genreRepository.findGenresByFilmsId(filmId);
	}

	@Override
	public Genre findByName(String name) {
		return genreRepository.findByName(name);
	}

	@Override
	public void updateFilmGenres(List<Genre> genresUpdate, Film film) {
		List<Genre> genres = findGenresByFilmsId(film.getId());

		// Новые жанры фильма
		List<Genre> missingInGenres = genresUpdate.stream()
				.filter(e -> genres.stream().noneMatch(g -> g.getName().equals(e.getName()))).toList();
		// Больше не жанры для этого фильма
		List<Genre> missingInGenresUpdate = genres.stream()
				.filter(e -> genresUpdate.stream().noneMatch(g -> g.getName().equals(e.getName()))).toList();

		addAMovieToAGenres(missingInGenres, film);

		for (Genre genre : missingInGenresUpdate) {
			genre.removeFilm(film.getId());
			genreRepository.save(genre);
		}
	}

	@Override
	public void addFilmGenres(List<Genre> genres, Film film) {
		addAMovieToAGenres(genres, film);
	}
	
	private void addAMovieToAGenres(List<Genre> genres,Film film) {
		for (Genre g : genres) {
			Genre genre = genreRepository.findByName(g.getName());
			if (genre == null) {
				g.addGenre();
				genreRepository.save(g);
				g.addFilm(film);
				genreRepository.save(g);
			}
			else {
				genre.addFilm(film);
				genreRepository.save(genre);
			}
		}
	}

}
