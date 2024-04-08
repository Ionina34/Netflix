package ru.netflix.service.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.netflix.model.Country;
import ru.netflix.repository.CountryRepository;
import ru.netflix.service.CountryService;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {
	private final CountryRepository countryRepository;
	
	@Override
	public List<Country> findCountriesByFilmsId(Long filmId){
		return countryRepository.findCountriesByFilmsId(filmId);
	}
}
