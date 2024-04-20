package ru.netflix.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.netflix.model.Country;
import ru.netflix.repository.CountryRepository;
import ru.netflix.service.CountryService;

@Service
public class CountryServiceImpl implements CountryService {
	@Autowired
	private CountryRepository countryRepository;
	
	@Override
	public List<Country> findCountriesByFilmsId(Long filmId){
		return countryRepository.findCountriesByFilmsId(filmId);
	}
}
