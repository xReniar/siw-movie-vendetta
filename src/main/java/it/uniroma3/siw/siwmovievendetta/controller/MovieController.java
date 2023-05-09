package it.uniroma3.siw.siwmovievendetta.controller;

import it.uniroma3.siw.siwmovievendetta.model.Movie;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MovieController {
    @GetMapping("formNewMovie")
    public String newMovie(Model model){
        model.addAttribute("movie",new Movie());
        return "formNewMovie.html";
    }
}
