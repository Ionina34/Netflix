package ru.netflix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ru.netflix.model.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
	/** Запрос для получения оценки фильма */
	@Query("SELECT r FROM Rating r "
			  + "JOIN r.user u WHERE u.id = :userId AND r.film.id = :filmId")
	    List<Rating> getRatingByUserIdAndFilmId(@Param("userId") Long userId, @Param("filmId") Long filmId);
}
