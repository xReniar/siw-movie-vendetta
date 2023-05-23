package it.uniroma3.siw.siwmovievendetta.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.Set;
import java.util.Objects;

@Entity
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String name;
    @NotBlank
    private String surname;

    private LocalDate birthDate;
    private LocalDate deathDate;

    @OneToOne
    private Image profilePicture;

    @OneToMany(mappedBy = "director",fetch = FetchType.LAZY)
    private Set<Movie> directedMovies;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Movie> actedMovies;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(LocalDate deathDate) {
        this.deathDate = deathDate;
    }

    public Image getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Image profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Set<Movie> getDirectedMovies() {
        return directedMovies;
    }

    public void setDirectedMovies(Set<Movie> directedMovies) {
        this.directedMovies = directedMovies;
    }

    public Set<Movie> getActedMovies() {
        return actedMovies;
    }

    public void setActedMovies(Set<Movie> actedMovies) {
        this.actedMovies = actedMovies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artist artist = (Artist) o;
        return Objects.equals(id, artist.id) && Objects.equals(name, artist.name) && Objects.equals(surname, artist.surname) && Objects.equals(birthDate, artist.birthDate) && Objects.equals(deathDate, artist.deathDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, birthDate, deathDate);
    }
}
