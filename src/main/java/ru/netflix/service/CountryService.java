package ru.netflix.service;

import java.util.List;

import ru.netflix.model.Country;

public interface CountryService {
	List<Country> findCountriesByFilmsId(Long filmId);
}
