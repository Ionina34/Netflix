package ru.netflix.service.Impl;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.netflix.model.Genre;
import ru.netflix.repository.GenreRepository;
import ru.netflix.service.GenreService;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService{
	
	private final GenreRepository genreRepository;

	@Override
	public List<Genre> getRandomGenres() {
		return genreRepository.getTreeRanfomValues();
	}

	@Override
	public List<Genre> findGenresByFilmsId(Long filmId) {
		return genreRepository.findGenresByFilmsId(filmId);
	}

}
