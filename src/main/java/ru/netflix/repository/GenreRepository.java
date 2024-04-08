package ru.netflix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ru.netflix.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
	@Query(value="SELECT id,name,created_at,updated_at FROM genres ORDER BY RAND() LIMIT 3",
			nativeQuery = true)
	List<Genre> getTreeRanfomValues();
	
	List<Genre> findGenresByFilmsId(Long genreId);
	}
