package it.uniroma3.siw.siwmovievendetta.repository;

import it.uniroma3.siw.siwmovievendetta.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {

}
