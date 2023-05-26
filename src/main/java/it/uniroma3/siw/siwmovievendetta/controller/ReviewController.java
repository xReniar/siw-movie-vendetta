package it.uniroma3.siw.siwmovievendetta.controller;

import it.uniroma3.siw.siwmovievendetta.controller.validator.ReviewValidator;
import it.uniroma3.siw.siwmovievendetta.model.Artist;
import it.uniroma3.siw.siwmovievendetta.model.Movie;
import it.uniroma3.siw.siwmovievendetta.model.Review;
import it.uniroma3.siw.siwmovievendetta.repository.MovieRepository;
import it.uniroma3.siw.siwmovievendetta.repository.ReviewRepository;
import it.uniroma3.siw.siwmovievendetta.service.MovieService;

import java.util.HashSet;
import java.util.Set;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReviewController {
    @Autowired
    MovieRepository movieRepository;

    @Autowired
    MovieService movieService;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ReviewValidator reviewValidator;

    @Autowired
    GlobalController globalController;

    @PostMapping("/user/uploadReview/{movieId}")
    public String newReview(Model model, @Valid @ModelAttribute("review") Review review, BindingResult bindingResult, @PathVariable("movieId") Long id) {
        this.reviewValidator.validate(review,bindingResult);
        UserDetails user = this.globalController.getUser();
        Movie movie = this.movieRepository.findById(id).get();
        if(user != null && !movie.getReviews().contains(review)){
            review.setAuthor(this.globalController.getUser().getUsername());
            this.reviewRepository.save(review);
            movie.getReviews().add(review);
        }
        this.movieRepository.save(movie);

        return function(model, movie, user);
    }

    @GetMapping("/user/deleteReview/{movieId}/{reviewId}")
    public String removeReview(Model model, @PathVariable("movieId") Long movieId,@PathVariable("reviewId") Long reviewId){
        Movie movie = this.movieRepository.findById(movieId).get();
        Review review = this.reviewRepository.findById(reviewId).get();

        movie.getReviews().remove(review);
        this.reviewRepository.delete(review);
        this.movieRepository.save(movie);

        UserDetails user = this.globalController.getUser();
        return function(model, movie, user);
    }

    public String function(Model model,Movie movie,UserDetails user){
        Set<Artist> movieCast = new HashSet<>();
        if (movie.getActors() != null) {
            movieCast.addAll(movie.getActors());
        }
        if (movie.getDirector() != null) {
            movieCast.add(movie.getDirector());
        }
        model.addAttribute("movieCast", movieCast);
        model.addAttribute("movie", movie);
        model.addAttribute("director", movie.getDirector());
        if(user != null){
            if(this.movieService.alreadyReviewed(movie.getReviews(),user.getUsername())){
                model.addAttribute("hasComment", true);
            } else {
                model.addAttribute("hasComment", false);
            }
            model.addAttribute("review", new Review());
        } else {
            model.addAttribute("hasComment", false);
            model.addAttribute("review", new Review());
        }
        model.addAttribute("reviews", movie.getReviews());
        return "movie.html";
    }
}
