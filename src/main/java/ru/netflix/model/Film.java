package ru.netflix.model;


import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="films")
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
	
	@Column(name="create_at")
	private LocalDate create_at;
	
	@Column(name="update_at")
	private LocalDate update_at;
	
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
}
