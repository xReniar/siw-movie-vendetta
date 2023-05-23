package it.uniroma3.siw.siwmovievendetta.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.siwmovievendetta.model.Movie;
import it.uniroma3.siw.siwmovievendetta.model.Artist;
import it.uniroma3.siw.siwmovievendetta.repository.ArtistRepository;
import it.uniroma3.siw.siwmovievendetta.repository.MovieRepository;
import jakarta.transaction.Transactional;

@Service
public class MovieService {
    @Autowired
    ArtistRepository artistRepository;

    @Autowired
    MovieRepository movieRepository;

    @Transactional
    public List<Movie> getSearchedMovies(String title){
        return this.movieRepository.findByTitle(title);
    }

    @Transactional
    public List<Artist> getPossibleDirectors(Long id){
        return this.artistRepository.getByDirectedMoviesNotContaining(this.movieRepository.findById(id).get());
    }

    @Transactional
    public void setDirectorToMovie(Movie movie,Long artistId){
        Artist oldDirector = movie.getDirector();
        Artist newDirector = this.artistRepository.findById(artistId).get();

        if(oldDirector != null){
            oldDirector.getDirectedMovies().remove(movie);
            this.artistRepository.save(oldDirector);
        }

        newDirector.getDirectedMovies().add(movie);
        movie.setDirector(newDirector);

        this.artistRepository.save(newDirector);
        this.movieRepository.save(movie);
    }

    @Transactional
    public void setActorToMovie(Movie movie,Long artistId){
        Artist actor = this.artistRepository.findById(artistId).get();

        actor.getActedMovies().add(movie);
        movie.getActors().add(actor);

        this.artistRepository.save(actor);
        this.movieRepository.save(movie);
    }

    @Transactional
    public void removeActorToMovie(Movie movie, Long artistId){
        Artist actor = this.artistRepository.findById(artistId).get();

        actor.getActedMovies().remove(movie);
        movie.getActors().remove(actor);

        this.artistRepository.save(actor);
        this.movieRepository.save(movie);
    }
}
