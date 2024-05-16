package ru.netflix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.netflix.model.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
	List<Country> findCountriesByFilmsId(Long countryId);
	
	Country findByName(String country);
}
