package it.uniroma3.siw.siwmovievendetta.repository;

import it.uniroma3.siw.siwmovievendetta.model.Image;
import org.springframework.data.repository.CrudRepository;

public interface PictureRepository extends CrudRepository<Image,Long> {
}
