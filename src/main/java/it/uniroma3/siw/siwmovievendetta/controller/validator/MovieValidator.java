package it.uniroma3.siw.siwmovievendetta.controller.validator;

import it.uniroma3.siw.siwmovievendetta.model.Movie;
import it.uniroma3.siw.siwmovievendetta.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MovieValidator implements Validator {
    @Autowired
    private MovieRepository movieRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return Movie.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Movie movie = (Movie) target;
        if(movie.getTitle() != null && movie.getYear() != null
            && movieRepository.existsByTitleAndYear(movie.getTitle(),movie.getYear())){
            errors.reject("movie.duplicate");
        }
    }
}
