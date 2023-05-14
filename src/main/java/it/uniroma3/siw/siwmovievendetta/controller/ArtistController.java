package it.uniroma3.siw.siwmovievendetta.controller;

import it.uniroma3.siw.siwmovievendetta.model.Artist;
import it.uniroma3.siw.siwmovievendetta.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ArtistController {
    @Autowired
    ArtistRepository artistRepository;

    @GetMapping("/admin/formNewArtist")
    public String newArtist(Model model){
        model.addAttribute("artist",new Artist());
        return "formNewArtist.html";
    }



}
