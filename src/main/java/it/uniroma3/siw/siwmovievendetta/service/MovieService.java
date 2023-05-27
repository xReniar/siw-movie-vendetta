package it.uniroma3.siw.siwmovievendetta.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.siwmovievendetta.model.Movie;
import it.uniroma3.siw.siwmovievendetta.model.Review;
import it.uniroma3.siw.siwmovievendetta.model.Artist;
import it.uniroma3.siw.siwmovievendetta.model.Image;
import it.uniroma3.siw.siwmovievendetta.repository.ArtistRepository;
import it.uniroma3.siw.siwmovievendetta.repository.ImageRepository;
import it.uniroma3.siw.siwmovievendetta.repository.MovieRepository;
import jakarta.transaction.Transactional;

@Service
public class MovieService {
    @Autowired
    ArtistRepository artistRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    ImageRepository imageRepository;

    @Transactional
    public void createMovie(Movie movie, MultipartFile image) throws IOException {
        Image movieImg = new Image(image.getBytes());
        this.imageRepository.save(movieImg);

        movie.setImage(movieImg);
        this.movieRepository.save(movie);
    }

    @Transactional
    public List<Movie> getSearchedMovies(String title) {
        return this.movieRepository.findByTitle(title);
    }

    @Transactional
    public List<Artist> getPossibleDirectors(Long id) {
        return this.artistRepository.getByDirectedMoviesNotContaining(this.movieRepository.findById(id).get());
    }

    @Transactional
    public void setDirectorToMovie(Movie movie, Long artistId) {
        Artist oldDirector = movie.getDirector();
        Artist newDirector = this.artistRepository.findById(artistId).get();

        if (oldDirector != null) {
            oldDirector.getDirectedMovies().remove(movie);
            this.artistRepository.save(oldDirector);
        }

        newDirector.getDirectedMovies().add(movie);
        movie.setDirector(newDirector);

        this.artistRepository.save(newDirector);
        this.movieRepository.save(movie);
    }

    @Transactional
    public void setActorToMovie(Movie movie, Long artistId) {
        Artist actor = this.artistRepository.findById(artistId).get();

        actor.getActedMovies().add(movie);
        movie.getActors().add(actor);

        this.artistRepository.save(actor);
        this.movieRepository.save(movie);
    }

    @Transactional
    public void removeActorToMovie(Movie movie, Long artistId) {
        Artist actor = this.artistRepository.findById(artistId).get();

        actor.getActedMovies().remove(movie);
        movie.getActors().remove(actor);

        this.artistRepository.save(actor);
        this.movieRepository.save(movie);
    }

    public String function(Model model,Movie movie,UserDetails user){
        Set<Artist> movieCast = new HashSet<>();
        if(movie.getActors() != null)
            movieCast.addAll(movie.getActors());
        movieCast.add(movie.getDirector());
        movieCast.remove(null);
        model.addAttribute("movieCast", movieCast);
        model.addAttribute("movie", movie);
        model.addAttribute("director", movie.getDirector());
        if(user != null && this.alreadyReviewed(movie.getReviews(),user.getUsername()))
            model.addAttribute("hasComment", true);
        else
            model.addAttribute("hasComment", false);
        model.addAttribute("review", new Review());
        model.addAttribute("reviews", movie.getReviews());
        return "movie.html";
    }

    @Transactional
    public boolean alreadyReviewed(Set<Review> reviews,String author){
        if(reviews != null)
            for(Review rev : reviews)
                if(rev.getAuthor().equals(author))
                    return true;
        return false;
    }
}
