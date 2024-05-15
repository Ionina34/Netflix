package ru.netflix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.netflix.model.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

}
