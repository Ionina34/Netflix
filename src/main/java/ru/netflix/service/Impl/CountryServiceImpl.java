package ru.netflix.service.Impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.netflix.model.Country;
import ru.netflix.model.Film;
import ru.netflix.model.Genre;
import ru.netflix.repository.CountryRepository;
import ru.netflix.service.interfaces.CountryService;

@Service
public class CountryServiceImpl implements CountryService {
	@Autowired
	private CountryRepository countryRepository;
	
	@Autowired
	public List<Country> getAll(){
		return countryRepository.findAll();
	}
	
	@Override
	public List<Country> findCountriesByFilmsId(Long filmId){
		return countryRepository.findCountriesByFilmsId(filmId);
	}

	@Override
	public Country findByName(String country) {
		return countryRepository.findByName(country);
	}
	
	@Override
	public void updateFilmCountries(List<Country> countriesUpdate,Film film) {
		List<Country> countries=findCountriesByFilmsId(film.getId());
		
		//Новые страны фильма
		List<Country> missingInCountries=countriesUpdate.stream()
				.filter(e->countries.stream().noneMatch(g->g.getName().equals(e.getName())))
				.toList();
		//Боьше не страны для этого фильма
		List<Country> missingInCountriesUpdate=countries.stream()
				.filter(e->countriesUpdate.stream().noneMatch(g->g.getName().equals(e.getName())))
				.toList();
		
		for(Country c: missingInCountries) {
			Country country=countryRepository.findByName(c.getName());
			if(country==null) {
				c.setCreated_at(LocalDate.now());
				c.setUpdated_at(LocalDate.now());
				countryRepository.save(c);
				c.addFilm(film);
				countryRepository.save(c);
			}
			else {
				country.addFilm(film);
				countryRepository.save(country);
			}
		}
		
		for(Country country: missingInCountriesUpdate) {
			country.removeFilm(film.getId());
			countryRepository.save(country);
		}
	}
}
