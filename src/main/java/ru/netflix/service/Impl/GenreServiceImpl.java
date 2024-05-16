package ru.netflix.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.netflix.model.Genre;
import ru.netflix.repository.GenreRepository;
import ru.netflix.service.interfaces.GenreService;

@Service
public class GenreServiceImpl implements GenreService{
	
	@Autowired
	private GenreRepository genreRepository;

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

}
