package it.uniroma3.siw.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.CredenzialiService;
import it.uniroma3.siw.service.UtenteService;
import jakarta.validation.Valid;

@Controller
public class AuthenticationController {
	
	private CredenzialiService credenzialiService;
	private UtenteService utenteService;
	private PasswordEncoder passwordEncoder;
	
	
	
	public AuthenticationController(CredenzialiService credenzialiService, UtenteService utenteService,
			PasswordEncoder passwordEncoder) {
		this.credenzialiService = credenzialiService;
		this.utenteService = utenteService;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/login")
	public String formLogin() {
		return "login";
	}
	
	@GetMapping("/register")
	public String formRegister(Model model) {
		model.addAttribute("utente", new Utente());
		model.addAttribute("credenziali", new Credenziali());
		return "register";
		
	}
	
	@PostMapping("/register")
	public String saveUtente(@Valid @ModelAttribute("credenziali") Credenziali credenziali, BindingResult bindingResultCredenziali,
			@Valid @ModelAttribute("utente") Utente utente, BindingResult bindingResultUtente, Model model) {
		
		if(bindingResultUtente.hasErrors() || bindingResultCredenziali.hasErrors()) {
			model.addAttribute("credenziali", credenziali);
	        model.addAttribute("utente", utente);
			return "register";
		}
		String password = credenziali.getPassword();
		String passwordCifrata = this.passwordEncoder.encode(password);
		credenziali.setRole("DEFAULT");
		credenziali.setUtente(utente);
		credenziali.setPassword(passwordCifrata);
		this.utenteService.save(utente);
		this.credenzialiService.save(credenziali);
		return "redirect:/login";
		
		
	}
	
	@GetMapping("/success")
	public String successoLogin() {
		return "redirect:/";
	}
}
