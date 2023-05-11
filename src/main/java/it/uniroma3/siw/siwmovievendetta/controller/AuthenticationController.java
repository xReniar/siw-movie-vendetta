package it.uniroma3.siw.siwmovievendetta.controller;

import it.uniroma3.siw.siwmovievendetta.service.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationController {
    @Autowired
    private CredentialsService credentialsService;

    @GetMapping("/login")
    public String showLoginForm (Model model) {
        return "login.html";
    }
}
