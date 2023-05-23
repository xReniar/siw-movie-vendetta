package it.uniroma3.siw.siwmovievendetta.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String title;

    @Min(1900)
    @Max(2023)
    private Integer year;

    @OneToOne
    Image image;

    @ManyToOne(fetch = FetchType.LAZY)
    private Artist director;

    @ManyToMany(mappedBy = "actedMovies", fetch = FetchType.LAZY)
    private List<Artist> actors;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Artist getDirector() {
        return director;
    }

    public void setDirector(Artist director) {
        this.director = director;
    }

    public List<Artist> getActors() {
        return actors;
    }

    public void setActors(List<Artist> actors) {
        this.actors = actors;
    }
}
