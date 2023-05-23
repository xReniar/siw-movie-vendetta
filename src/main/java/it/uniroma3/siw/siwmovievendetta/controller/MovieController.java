package it.uniroma3.siw.siwmovievendetta.controller;

import it.uniroma3.siw.siwmovievendetta.controller.validator.MovieValidator;
import it.uniroma3.siw.siwmovievendetta.model.Artist;
import it.uniroma3.siw.siwmovievendetta.model.Image;
import it.uniroma3.siw.siwmovievendetta.model.Movie;
import it.uniroma3.siw.siwmovievendetta.repository.ArtistRepository;
import it.uniroma3.siw.siwmovievendetta.repository.ImageRepository;
import it.uniroma3.siw.siwmovievendetta.repository.MovieRepository;
import it.uniroma3.siw.siwmovievendetta.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

@Controller
public class MovieController {

    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ArtistRepository artistRepository;
    @Autowired 
    private MovieService movieService;
    @Autowired
    private MovieValidator movieValidator;
    
    @GetMapping("/admin/formNewMovie")
    public String newMovie(Model model){
        model.addAttribute("movie",new Movie());
        return "admin/formNewMovie.html";
    }

    @PostMapping("/admin/uploadMovie")
    public String newMovie(Model model, @Valid @ModelAttribute("movie") Movie movie, BindingResult bindingResult, @RequestParam("file") MultipartFile image) throws IOException {
        this.movieValidator.validate(movie,bindingResult);
        if(!bindingResult.hasErrors()){
            /*
             for(MultipartFile image : images){
                Image picture = new Image(image.getBytes());
                this.imageRepository.save(picture);
                movieImgs.add(picture);
            }
             */
            Image movieImg = new Image(image.getBytes());
            this.imageRepository.save(movieImg);

            movie.setImage(movieImg);
            this.movieRepository.save(movie);

            model.addAttribute("movie",movie);
            return "movie.html";
        } else {
            return "/admin/formNewMovie.html";
        }
    }

    @GetMapping("/admin/indexMovie")
    public String indexMovie(Model model){
        return "admin/indexMovie.html";
    }

    @GetMapping("/movies")
    public String showAllMovies(Model model){
        model.addAttribute("movies",this.movieRepository.findAll());
        return "movies.html";
    }

    @GetMapping("/movies/{movieId}")
    public String getMovie(Model model,@PathVariable("movieId") Long id){
        Movie movie = this.movieRepository.findById(id).get();
        model.addAttribute("movie",movie);

        Set<Artist> cast = movie.getActors();
        if(movie.getDirector() != null){
            cast.add(movie.getDirector());
        }

        model.addAttribute("cast", cast);
        model.addAttribute("director", movie.getDirector());

        return "movie.html";
    }

    @PostMapping("/searchMovie")
    public String searchMovie(Model model, @RequestParam String title){
        model.addAttribute("movies", this.movieService.getSearchedMovies(title));
        return "movies.html";
    }

    @GetMapping("/admin/manage/{movieId}")
    public String updateMovie(Model model,@PathVariable("movieId") Long id){
        Movie movie = this.movieRepository.findById(id).get();
        model.addAttribute("movie", movie);
        model.addAttribute("director", movie.getDirector());
        model.addAttribute("actors",movie.getActors());
        return "admin/formUpdateMovie.html";
    }

    @GetMapping("/admin/manage/updateDirector/{movieId}")
    public String updateMovieDirector(Model model,@PathVariable("movieId") Long id){
        Movie movie = this.movieRepository.findById(id).get();
        model.addAttribute("movie", movie);
        model.addAttribute("choices", this.artistRepository.getByDirectedMoviesNotContaining(movie));
        return "admin/formUpdateDirector.html";
    }

    @GetMapping("/admin/manage/setDirector/{movieId}/{artistId}")
    public String setMovieDirector(Model model,@PathVariable("movieId") Long movieId,@PathVariable("artistId") Long artistId){
        Movie movie = this.movieRepository.findById(movieId).get();
        this.movieService.setDirectorToMovie(movie, artistId);

        model.addAttribute("movie", movie);
        model.addAttribute("director", movie.getDirector());
        model.addAttribute("actors",movie.getActors());

        return "admin/formUpdateMovie.html";
    }
    
    @GetMapping("/admin/manage/removeDirector/{movieId}/{artistId}")
    public String removeMovieDirector(Model model,@PathVariable("movieId") Long movieId,@PathVariable("artistId") Long artistId){
        Movie movie = this.movieRepository.findById(movieId).get();
        Artist artist = this.artistRepository.findById(artistId).get();

        movie.setDirector(null);
        artist.getDirectedMovies().remove(movie);

        this.artistRepository.save(artist);
        this.movieRepository.save(movie);

        model.addAttribute("movie", movie);
        model.addAttribute("director", movie.getDirector());
        model.addAttribute("actors",movie.getActors());

        return "admin/formUpdateMovie.html";
    }

    @GetMapping("/admin/manage/addActor/{movieId}")
    public String updateMovieActor(Model model,@PathVariable("movieId") Long movieId){
        Movie movie = this.movieRepository.findById(movieId).get();
        model.addAttribute("movie", movie);
        model.addAttribute("choices", this.artistRepository.getByActedMoviesNotContaining(movie));
        return "admin/formAddActors.html";
    }

    @GetMapping("/admin/manage/setActor/{movieId}/{artistId}")
    public String setMovieActor(Model model,@PathVariable("movieId") Long movieId,@PathVariable("artistId") Long artistId){
        Movie movie = this.movieRepository.findById(movieId).get();
        this.movieService.setActorToMovie(movie, artistId);

        model.addAttribute("movie", movie);
        model.addAttribute("director", movie.getDirector());
        model.addAttribute("actors",movie.getActors());

        return "admin/formUpdateMovie.html";
    }

    @GetMapping("/admin/manage/removeActor/{movieId}/{artistId}")
    public String removeMovieActor(Model model,@PathVariable("movieId") Long movieId,@PathVariable("artistId") Long artistId){
        Movie movie = this.movieRepository.findById(movieId).get();
        this.movieService.removeActorToMovie(movie, artistId);
        model.addAttribute("movie", movie);
        model.addAttribute("director", movie.getDirector());
        model.addAttribute("actors",movie.getActors());

        return "admin/formUpdateMovie.html";
    }
}
