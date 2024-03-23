package ru.netflix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.netflix.model.Director;

public interface DirectorRepository extends JpaRepository<Director,Long> {
	List<Director> findDirectorsByFilmsId(Long directorId);
}
