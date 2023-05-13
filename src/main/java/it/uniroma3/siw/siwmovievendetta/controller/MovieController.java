package it.uniroma3.siw.siwmovievendetta.controller;

import it.uniroma3.siw.siwmovievendetta.controller.validator.MovieValidator;
import it.uniroma3.siw.siwmovievendetta.model.Image;
import it.uniroma3.siw.siwmovievendetta.model.Movie;
import it.uniroma3.siw.siwmovievendetta.repository.PictureRepository;
import it.uniroma3.siw.siwmovievendetta.repository.MovieRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MovieController {

    @Autowired
    private PictureRepository pictureRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MovieValidator movieValidator;
    
    @GetMapping("/formNewMovie")
    public String newMovie(Model model){
        model.addAttribute("movie",new Movie());
        return "formNewMovie.html";
    }

    @PostMapping("/uploadMovie")
    public String newMovie(Model model, @Valid @ModelAttribute("movie") Movie movie, BindingResult bindingResult, @RequestParam("file") MultipartFile[] images) throws IOException {
        this.movieValidator.validate(movie,bindingResult);
        if(!bindingResult.hasErrors()){
            List<Image> movieImgs = new ArrayList<>();

            for(MultipartFile image : images){
                Image picture = new Image(image.getBytes());
                this.pictureRepository.save(picture);
                movieImgs.add(picture);
            }

            movie.setImages(movieImgs);
            this.movieRepository.save(movie);

            model.addAttribute("movie",movie);
            model.addAttribute("pictures",movieImgs);
            return "movie.html";
        } else {
            return "formNewMovie.html";
        }
    }
}
