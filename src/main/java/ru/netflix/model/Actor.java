package ru.netflix.model;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.apache.commons.io.FileUtils;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "actors")
@NoArgsConstructor
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

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "actor_films", joinColumns = { @JoinColumn(name = "actor_id") }, inverseJoinColumns = {
			@JoinColumn(name = "film_id") })
	private Set<Film> films = new HashSet<>();

	public void addFilm(Film film) {
		this.films.add(film);
		film.getActors().add(this);
	}

	public void removeFilm(Long filmId) {
		Film film = this.films.stream().filter(t -> t.getId() == filmId).findFirst().orElse(null);
		if (film != null) {
			this.films.remove(film);
			film.getActors().remove(this);
		}
	}
	
	public void update(Actor updateActor) {
		this.name=updateActor.name;
		this.birthday=updateActor.birthday;
		this.brief_biography=updateActor.brief_biography;
		if (updateActor.photo != "") {
			updateActor.saveImage(updateActor.photo, updateActor.name);
			this.setPhoto(("actors/" + this.name + ".jpg"));
		}
		this.updated_at=LocalDate.now();
	}

	public void addActor(Actor actor) {
		if (actor.photo != "") {
			actor.saveImage(actor.photo, actor.name);
			this.setPhoto(("actors/" + this.name + ".jpg"));
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
			return properties.getProperty("image_actor.path");
		}
		catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
	}
}
