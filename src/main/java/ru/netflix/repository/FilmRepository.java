package ru.netflix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ru.netflix.model.Film;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {
	@Query(value=
			"SELECT r1.id,r1.create_at,r1.description,r1.image,r1.length,"
			+ "r1.name,r1.release_date,r1.update_at FROM films AS r1 JOIN"
			+ " (SELECT CEIL(RAND()* (SELECT MAX(ID)-14 FROM films)) AS id) AS r2 "
			+ "WHERE r1.id>=r2.id ORDER BY r1.id ASC LIMIT 10",
			nativeQuery = true)
	List<Film> getTenRandomValues();
	
	List<Film> findFilmsByActorsId(Long actorId);
	List<Film> findFilmsByCountriesId(Long countryId);
	List<Film> findFilmByDirectorsId(Long directorsId);
	List<Film> findFilmByGenresId(Long genresId);
	List<Film> findFilmByScreenwritersId(Long screewriterId);
}
