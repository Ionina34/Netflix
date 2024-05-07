package ru.netflix.model;


import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="films")
@NoArgsConstructor
@Getter
@Setter
public class Film {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="description")
	private String des;
	
	@Column(name="release_date")
	private LocalDate release_date;
	private String length;
	
	@Column(name="created_at")
	private LocalDate created_at;
	
	@Column(name="updated_at")
	private LocalDate updated_at;
	
	@Column(name="image")
	private String image;
	
	@ManyToMany(fetch = FetchType.LAZY,
		      cascade = {
		          CascadeType.REMOVE,
		          CascadeType.REFRESH
		      },
		      mappedBy = "films")
	@JsonIgnore
	private Set<Actor> actors = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.LAZY,
			cascade = {CascadeType.REMOVE,
					CascadeType.REFRESH},
			mappedBy = "films")
	@JsonIgnore
	private Set<Country> countries=new HashSet<>();
	
	@ManyToMany(fetch = FetchType.LAZY,
			cascade = {CascadeType.REMOVE,
					CascadeType.REFRESH},
			mappedBy = "films")
	@JsonIgnore
	private Set<Director> directors=new HashSet<>();
	
	@ManyToMany(fetch = FetchType.LAZY,
			cascade = {CascadeType.REMOVE,
					CascadeType.REFRESH},
			mappedBy = "films")
	@JsonIgnore
	private Set<Genre> genres=new HashSet<>();
	
	@ManyToMany(fetch = FetchType.LAZY,
			cascade = {CascadeType.REMOVE,
					CascadeType.REFRESH},
			mappedBy = "films")
	@JsonIgnore
	private Set<Screenwriter> screenwriters=new HashSet<>();
	
	@ManyToMany(fetch = FetchType.LAZY,
			cascade = {CascadeType.REMOVE,
					CascadeType.REFRESH},
			mappedBy = "films")
	@JsonIgnore
	private Set<User> users=new HashSet<>();
}