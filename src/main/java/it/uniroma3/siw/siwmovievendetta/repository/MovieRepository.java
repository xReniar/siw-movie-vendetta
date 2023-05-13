package it.uniroma3.siw.siwmovievendetta.repository;

import it.uniroma3.siw.siwmovievendetta.model.Movie;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie,Long> {
    public boolean existsByTitleAndYear(String title,Integer year);
}
