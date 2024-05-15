package ru.netflix.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="users")
@NoArgsConstructor
@Getter
@Setter
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	private String email;
	private String password;
	private String roles;
	
	private LocalDate created_at;
	private LocalDate updated_at;
	
	@ManyToMany(fetch = FetchType.LAZY,
			cascade = { CascadeType.REMOVE, CascadeType.REFRESH })
	@JoinTable(name = "favourites", 
			joinColumns = { @JoinColumn(name = "user_id") }, 
			inverseJoinColumns = { @JoinColumn(name = "film_id") })
	private Set<Film> films = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.LAZY,
			cascade = { CascadeType.REMOVE, CascadeType.REFRESH })
	@JoinTable(name = "user_film_raitings", 
			joinColumns = { @JoinColumn(name = "user_id") }, 
			inverseJoinColumns = { @JoinColumn(name = "film_id") })
	private Set<Film> ratedFilms = new HashSet<>();
	
	public void addFilm(Film film) {
	    this.films.add(film);
	    film.getUsers().add(this);
	  }
	  
	  public void removeFilm(Long filmId) {
	    Film film = this.films.stream().filter(t -> t.getId() == filmId)
	    		.findFirst().orElse(null);
	    if (film != null) {
	      this.films.remove(film);
	      film.getUsers().remove(this);
	    }
	  }
}
