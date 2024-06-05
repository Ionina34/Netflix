package ru.netflix.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ru.netflix.model.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long>
{
	Page<Actor> findAll(Pageable pageable);
	
	List<Actor> findActorsByFilmsId(Long filmId);
	
	Actor findByName(String name);
	
	@Query("SELECT a FROM Actor a "
			+"ORDER BY CASE WHEN :orderName = 'ASC' THEN a.name END ASC, "
			+"CASE WHEN :orderName = 'DESC' THEN a.name END DESC, "
			+"CASE WHEN :orderBirthday = 'ASC' THEN a.birthday END ASC, "
			+"CASE WHEN :orderBirthday = 'DESC' THEN a.birthday END DESC, "
			+"CASE WHEN :orederCreatedDate = 'ASC' THEN a.created_at END ASC, "
			+"CASE WHEN :orederCreatedDate = 'DESC' THEN a.created_at END DESC")
	Page<Actor> getActorsOrderByNameAndBirthdayAndCreatedDate(Pageable pageable,@Param("orderName") String name,
			@Param("orderBirthday") String birthday,@Param("orederCreatedDate") String created_date);
}
