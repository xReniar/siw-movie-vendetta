package it.uniroma3.siw.siwmovievendetta.controller;

import it.uniroma3.siw.siwmovievendetta.model.Picture;
import it.uniroma3.siw.siwmovievendetta.model.Movie;
import it.uniroma3.siw.siwmovievendetta.repository.PictureRepository;
import it.uniroma3.siw.siwmovievendetta.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    @GetMapping("/formNewMovie")
    public String newMovie(Model model){
        model.addAttribute("movie",new Movie());
        return "formNewMovie.html";
    }

    @PostMapping("/uploadMovie")
    public String uploadMovie(Model model, @ModelAttribute("movie") Movie movie, @RequestParam("file") MultipartFile[] images) throws IOException {
        List<Picture> pictures = new ArrayList<>();

        for(MultipartFile image : images){
            Picture picture = new Picture(image.getBytes());
            this.pictureRepository.save(picture);
            pictures.add(picture);
        }

        movie.setPictures(pictures);
        this.movieRepository.save(movie);

        model.addAttribute("movie",movie);
        model.addAttribute("pictures",pictures);
        return "movie.html";
    }
}
