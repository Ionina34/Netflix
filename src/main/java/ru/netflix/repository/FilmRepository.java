package ru.netflix.repository;

import java.util.List;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.persistence.TypedQuery;
import ru.netflix.model.Film;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {
	@Query(value=
			"SELECT r1.id,r1.created_at,r1.description,r1.image,r1.length,"
			+ "r1.name,r1.release_date,r1.updated_at FROM films AS r1 JOIN"
			+ " (SELECT CEIL(RAND()* (SELECT MAX(ID)-14 FROM films)) AS id) AS r2 "
			+ "WHERE r1.id>=r2.id ORDER BY r1.id ASC LIMIT 10",
			nativeQuery = true)
	List<Film> getTenRandomValues();
	
	@Query(value="SELECT f.id,f.created_at,f.description,f.image,f.length,"
			+"f.name,f.release_date,f.updated_at"
			+ " FROM films AS f"
			+ " JOIN ("
			+ " SELECT r.film_id, AVG(r.value) AS average_rating"
			+ " FROM user_film_raitings r"
			+ " GROUP BY r.film_id"
			+ " ORDER BY average_rating DESC"
			+ "  LIMIT 10"
			+ ") AS top_rated ON f.id = top_rated.film_id;",
			nativeQuery = true)
	List<Film> findTop10ByAverageRating();
	
	Page<Film> findAll(Pageable pageable);
	Page<Film> findByNameContainingIgnoreCase(String filter,Pageable pageable);
	Page<Film> findFilmsByUsersId(Long userID,Pageable pageable);
	
	List<Film> findFilmsByActorsId(Long actorId);
	List<Film> findFilmsByCountriesId(Long countryId);
	List<Film> findFilmsByDirectorsId(Long directorsId);
	List<Film> findFilmsByGenresId(Long genresId);
	List<Film> findFilmsByScreenwritersId(Long screewriterId);
	List<Film> findFilmsByUsersId(Long userId);
	
	  @Query("SELECT f FROM Film f "
			  + "JOIN f.users u WHERE u.id = :userId AND f.id = :filmId")
	    List<Film> getFavFilmByUserIdAndFilmId(@Param("userId") Long userId, @Param("filmId") Long filmId);
	
}
