package it.uniroma3.siw.siwmovievendetta.repository;

import it.uniroma3.siw.siwmovievendetta.model.Artist;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;

public interface ArtistRepository extends CrudRepository<Artist,Long> {
    public boolean existsByNameAndSurname(String name, String surname);
}
