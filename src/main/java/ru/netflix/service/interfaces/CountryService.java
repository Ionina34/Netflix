package ru.netflix.service.interfaces;

import java.util.List;

import ru.netflix.model.Country;
import ru.netflix.model.Film;

public interface CountryService {
	List<Country> getAll();
	List<Country> findCountriesByFilmsId(Long filmId);
	
	Country findByName(String country);
	
	void updateFilmCountries(List<Country> countriesUpdate,Film film);
	void addFilmCountries(List<Country> countries,Film film);
}
