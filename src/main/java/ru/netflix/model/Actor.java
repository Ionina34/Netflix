package ru.netflix.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "actors")
public class Actor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "birthday")
	private LocalDate birthday;

	@Column(name = "brief_biography")
	private String brief_biography;

	@Column(name = "created_at")
	private LocalDate created_at;

	@Column(name = "updated_at")
	private LocalDate updated_at;

	@Column(name = "photo")
	private String photo;

	@ManyToMany(fetch = FetchType.LAZY,
			cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "actor_films", 
			joinColumns = { @JoinColumn(name = "actor_id") }, 
			inverseJoinColumns = { @JoinColumn(name = "film_id") })
	private Set<Film> films = new HashSet<>();
	
	public void addFilm(Film film) {
	    this.films.add(film);
	    film.getActors().add(this);
	  }
	  
	  public void removeFilm(Long filmId) {
	    Film film = this.films.stream().filter(t -> t.getId() == filmId)
	    		.findFirst().orElse(null);
	    if (film != null) {
	      this.films.remove(film);
	      film.getActors().remove(this);
	    }
	  }
}
