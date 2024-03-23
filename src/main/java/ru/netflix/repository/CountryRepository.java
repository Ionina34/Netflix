package ru.netflix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.netflix.model.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {
	List<Country> findCountriesByFilmsId(Long countryId);
}
