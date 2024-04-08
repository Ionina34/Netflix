package ru.netflix.service;

import java.util.List;

import ru.netflix.model.Director;

public interface DirectorService {
	List<Director> findDirectorsByFilmsId(Long filmId);
}
