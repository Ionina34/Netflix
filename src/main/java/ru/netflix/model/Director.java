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

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "directors")
public class Director {
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

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "director_films", joinColumns = { @JoinColumn(name = "director_id") }, inverseJoinColumns = {
			@JoinColumn(name = "film_id") })
	private Set<Film> films = new HashSet<>();

	public void addFilm(Film film) {
		this.films.add(film);
		film.getDirectors().add(this);
	}

	public void removeFilm(Long filmId) {
		Film film = this.films.stream().filter(t -> t.getId() == filmId).findFirst().orElse(null);
		if (film != null) {
			this.films.remove(film);
			film.getDirectors().remove(this);
		}
	}

	public void addDirector(Director director) {
		if (director.photo != "") {
			director.saveImage(director.photo, director.name);
			this.setPhoto(("directors/" + this.name + ".jpg"));
		}
		this.setCreated_at(LocalDate.now());
		this.setUpdated_at(LocalDate.now());
	}
	
	public void update(Director updateDirector) {
		this.name=updateDirector.name;
		this.birthday=updateDirector.birthday;
		this.brief_biography=updateDirector.brief_biography;
		if (updateDirector.photo != "") {
			updateDirector.saveImage(updateDirector.photo, updateDirector.name);
			this.setPhoto(("directors/" + this.name + ".jpg"));
		}
		this.updated_at=LocalDate.now();
	}
	
	public void update(Screenwriter updateDirector) {
		this.name=updateDirector.getName();
		this.birthday=updateDirector.getBirthday();
		this.brief_biography=updateDirector.getBrief_biography();
		if (updateDirector.getPhoto() != "") {
			this.saveImage(updateDirector.getPhoto(), updateDirector.getName());
			this.setPhoto(("directors/" + this.name + ".jpg"));
		}
		this.updated_at=LocalDate.now();
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
			return properties.getProperty("image_director.path");
		}
		catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
	}
}
