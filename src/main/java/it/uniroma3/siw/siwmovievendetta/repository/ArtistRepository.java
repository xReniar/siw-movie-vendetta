package it.uniroma3.siw.siwmovievendetta.repository;

import it.uniroma3.siw.siwmovievendetta.model.Artist;
import it.uniroma3.siw.siwmovievendetta.model.Movie;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


public interface ArtistRepository extends CrudRepository<Artist,Long> {
    public boolean existsByNameAndSurname(String name, String surname);
    public List<Artist> findByName(String name);

    public List<Artist> getByDirectedMoviesNotContaining(Movie movie);
    public List<Artist> getByActedMoviesNotContaining(Movie movie);
}
