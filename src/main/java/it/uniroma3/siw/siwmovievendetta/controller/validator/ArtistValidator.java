package it.uniroma3.siw.siwmovievendetta.controller.validator;

import it.uniroma3.siw.siwmovievendetta.model.Artist;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ArtistValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Artist.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Artist artist = (Artist) target;

    }
}
