package ru.netflix.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "country")
public class Country {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name = "created_at")
	private LocalDate created_at;

	@Column(name = "updated_at")
	private LocalDate updated_at;
	
	@ManyToMany(fetch = FetchType.LAZY,
			cascade = {CascadeType.REMOVE,
					CascadeType.REFRESH})
	@JoinTable(name="country_films",
			joinColumns = {@JoinColumn(name="country_id")},
			inverseJoinColumns = {@JoinColumn(name="film_id")})
	private Set<Film> films=new HashSet<>();
	
	public void addFilm(Film film) {
		this.films.add(film);
		film.getCountries().add(this);
	}
	
	public void removeFilm(Long filmId) {
		Film film=this.films.stream().filter(t->t.getId()==filmId)
				.findFirst().orElse(null);
		if(film!=null) {
			this.films.remove(film);
			film.getCountries().remove(this);
		}
	}
}
