package ru.netflix.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.persistence.TypedQuery;
import ru.netflix.model.Film;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> ,PagingAndSortingRepository<Film, Long>{
	Film findByName(String name);
	
	/**Для получения 1 раномного фильма*/
	@Query(value=
			"SELECT r1.id,r1.created_at,r1.description,r1.image,r1.length,"
			+ "r1.name,r1.release_date,r1.updated_at,f.views FROM films AS r1 JOIN"
			+ " (SELECT CEIL(RAND()* (SELECT MAX(ID)-14 FROM films)) AS id) AS r2 "
			+ "WHERE r1.id>=r2.id LIMIT 1",
			nativeQuery = true)
	Film getRandomValues();
	
	/** Для получени 10 самых рейтинговых фильмов */
	@Query(value="SELECT f.id,f.created_at,f.description,f.image,f.length,"
			+"f.name,f.release_date,f.updated_at, f.views"
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
	
	@Query("SELECT f FROM Film AS f ORDER BY f.views DESC LIMIT 15")
	List<Film> findAllByOrderByViewsAsc();
	
	/** Для поучения фильмов, которые вышли за последний год */
	@Query("SELECT f FROM Film AS f WHERE f.release_date BETWEEN :startDate AND :endDate")
	List<Film> getNewFilms( @Param("startDate") LocalDate startDate,  @Param("endDate")LocalDate endDate);
	
	/** Для получения фильмов с сортировкой и фильтрацией */
	@Query("SELECT f FROM Film f JOIN f.genres g JOIN f.countries c "
			+ "WHERE (:genreName IS NULL OR g.name = :genreName) "
			+ "AND (:countryName IS NULL OR c.name = :countryName) "
			+ "ORDER BY CASE WHEN :orderData = 'ASC' "
			+ "THEN f.release_date END ASC, CASE WHEN :orderData = 'DESC' "
			+ "THEN f.release_date END DESC"
			+ ", CASE WHEN :orderName = 'ASC' "
			+ "THEN f.name END ASC, CASE WHEN :orderName = 'DESC' "
			+ "THEN f.name END DESC")
	Page<Film> findByGenresNameAndCountriesNameOrderByReleaseDateOrderByName(
			@Param("genreName") String genreName,
			@Param("countryName")String countryName, 
			@Param("orderData")String orderData,
			@Param("orderName") String orderName,
			Pageable pageable);
	
	/** Для получения фильмов с сортировкой без фильтрации */
	@Query("SELECT f FROM Film f "
			+"ORDER BY CASE WHEN :orderData = 'ASC' THEN f.release_date END ASC, "
			+"CASE WHEN :orderData = 'DESC' THEN f.release_date END DESC, "
			+"CASE WHEN :orderName = 'ASC' THEN f.name END ASC, "
			+ "CASE WHEN :orderName = 'DESC' THEN f.name END DESC")
	Page<Film> getFilmsOrderByNameAndReleaseDate(
			@Param("orderData")String orderData,
			@Param("orderName") String orderName,
			Pageable pageable);
	
	/** Для поиска фильмов по его названию */
	Page<Film> findByNameContainingIgnoreCase(String filter,Pageable pageable);
	
	//Запросы для получения Page<Film>
	Page<Film> findAll(Pageable pageable);
	Page<Film> findFilmsByUsersId(Long userID,Pageable pageable);
	Page<Film> findFilmsByGenresId(Long genreId,Pageable pageable);
	Page<Film> findFilmsByCountriesId(Long countryId,Pageable pageable);
    
	//Запросы для получения List<Film>
	List<Film> findFilmsByActorsId(Long actorId);
	List<Film> findFilmsByCountriesId(Long countryId);
	List<Film> findFilmsByDirectorsId(Long directorsId);
	List<Film> findFilmsByGenresId(Long genresId);
	List<Film> findFilmsByScreenwritersId(Long screewriterId);
	List<Film> findFilmsByUsersId(Long userId);
	
	/** Запрос для нахождения фильма в избранном */
	  @Query("SELECT f FROM Film f "
			  + "JOIN f.users u WHERE u.id = :userId AND f.id = :filmId")
	    List<Film> getFavFilmByUserIdAndFilmId(@Param("userId") Long userId, @Param("filmId") Long filmId);
}