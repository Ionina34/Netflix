package ru.netflix.service.interfaces;

import java.util.List;

import ru.netflix.model.Director;

public interface DirectorService {
	List<Director> findDirectorsByFilmsId(Long filmId);
}
