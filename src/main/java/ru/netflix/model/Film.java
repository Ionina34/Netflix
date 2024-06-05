package ru.netflix.model;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "films")
@NoArgsConstructor
@Getter
@Setter
public class Film {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String des;

	@Column(name = "release_date")
	private LocalDate release_date;
	private String length;

	@Column(name = "created_at")
	private LocalDate created_at;

	@Column(name = "updated_at")
	private LocalDate updated_at;

	@Column(name = "image")
	private String image;

	@ManyToMany(fetch = FetchType.LAZY,
			cascade = { CascadeType.REMOVE, CascadeType.REFRESH }, 
			mappedBy = "films")
	@JsonIgnore
	private Set<Actor> actors = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY, 
			cascade = { CascadeType.REMOVE, CascadeType.REFRESH }, 
			mappedBy = "films")
	@JsonIgnore
	private Set<Country> countries = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY,
			cascade = { CascadeType.REMOVE, CascadeType.REFRESH },
			mappedBy = "films")
	@JsonIgnore
	private Set<Director> directors = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY,
			cascade = { CascadeType.REMOVE, CascadeType.REFRESH },
			mappedBy = "films")
	@JsonIgnore
	private Set<Genre> genres = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY,
			cascade = { CascadeType.REMOVE, CascadeType.REFRESH },
			mappedBy = "films")
	@JsonIgnore
	private Set<Screenwriter> screenwriters = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY, 
			cascade = { CascadeType.REMOVE, CascadeType.REFRESH }, 
			mappedBy = "films")
	@JsonIgnore
	private Set<User> users = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY, 
			cascade = { CascadeType.REMOVE, CascadeType.REFRESH }, 
			mappedBy = "ratedFilms")
	@JsonIgnore
	private Set<User> userWhoRated = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.LAZY, 
			cascade = { CascadeType.REMOVE, CascadeType.REFRESH }, 
			mappedBy = "commentedFilms")
	@JsonIgnore
	private Set<User> userWhoCommented = new HashSet<>();

	public void update(Film updateFilm) {
		this.name = updateFilm.name;
		this.des = updateFilm.des;
		this.release_date = updateFilm.release_date;
		this.length = updateFilm.length;
		if (updateFilm.image != "") {
			updateFilm.saveImage(updateFilm.image, updateFilm.name);
			this.setImage(("films/" + this.name + ".jpg"));
		}
		this.setUpdated_at(LocalDate.now());
	}
	
	public void addFilm(Film film) {
		if (film.image != "") {
			film.saveImage(film.image, film.name);
			this.setImage(("films/" + this.name + ".jpg"));
		}
		this.setCreated_at(LocalDate.now());
		this.setUpdated_at(LocalDate.now());
	}

	private void saveImage(String image_path, String name) {
		try {
			String basePath = loadFilePath();
			String filename = basePath + name + ".jpg";
			// connectionTimeout, readTimeout = 10 seconds
			FileUtils.copyURLToFile(new URL(image_path), new File(filename), 10000, 10000);

		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	private String loadFilePath() {
		Properties properties=new Properties();
		Resource resource=new ClassPathResource("config.properties");
		
		try(InputStream inputStream=resource.getInputStream()){
			properties.load(inputStream);
			return properties.getProperty("image_films.path");
		}
		catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
	}
}