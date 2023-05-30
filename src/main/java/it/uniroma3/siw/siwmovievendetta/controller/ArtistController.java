package it.uniroma3.siw.siwmovievendetta.controller;

import it.uniroma3.siw.siwmovievendetta.controller.validator.ArtistValidator;
import it.uniroma3.siw.siwmovievendetta.model.Artist;
import it.uniroma3.siw.siwmovievendetta.model.Image;
import it.uniroma3.siw.siwmovievendetta.repository.ArtistRepository;
import it.uniroma3.siw.siwmovievendetta.repository.ImageRepository;
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

@Controller
public class ArtistController {
    @Autowired
    ArtistRepository artistRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    ArtistValidator artistValidator;

    @GetMapping("/admin/indexArtist")
    public String indexArtist(){
        return "/admin/indexArtist.html";
    }

    @GetMapping("/admin/formNewArtist")
    public String newArtist(Model model){
        model.addAttribute("artist",new Artist());
        return "/admin/formNewArtist.html";
    }

    @PostMapping("/admin/uploadArtist")
    public String newArtist(Model model, @Valid @ModelAttribute("artist") Artist artist, BindingResult bindingResult, @RequestParam("file") MultipartFile image) throws IOException {
        this.artistValidator.validate(artist,bindingResult);
        if(!bindingResult.hasErrors()){
            Image picture = new Image(image.getBytes());
            this.imageRepository.save(picture);
            artist.setProfilePicture(picture);

            this.artistRepository.save(artist);

            model.addAttribute("artist",artist);
            return "artist.html";
        } else {
            return "/admin/formNewArtist.html";
        }
    }

    @GetMapping("/artists")
    public String showAllArtists(Model model){
        model.addAttribute("artists",this.artistRepository.findAll());
        return "artists.html";
    }

    @GetMapping("/artists/{artistId}")
    public String getArtist(Model model,@PathVariable("artistId") Long id){
        Artist artist = this.artistRepository.findById(id).get();
        model.addAttribute("artist", this.artistRepository.findById(id).get());

        model.addAttribute("directed", artist.getDirectedMovies());
        model.addAttribute("acted", artist.getActedMovies());
        return "artist.html";
    }

    @PostMapping("/searchArtist")
    public String searchMovie(Model model, @RequestParam String name) {
        if (name.length() == 0) {
            model.addAttribute("artists", this.artistRepository.findAll());
        } else {
            model.addAttribute("artists", this.artistRepository.findByName(name));
        }
        return "artists.html";
    }
}
