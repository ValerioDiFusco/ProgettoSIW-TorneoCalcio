package it.uniroma3.siw.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Commento;
import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.CommentoService;
import it.uniroma3.siw.service.CredenzialiService;
import it.uniroma3.siw.service.PartitaService;
import jakarta.validation.Valid;

@Controller
public class CommentoController {
	
	private CommentoService commentoService;
	private PartitaService partitaService;
	private CredenzialiService credenzialiService;
	
	public CommentoController(CommentoService commentoService,PartitaService partitaService,CredenzialiService credenzialiService) {
		this.commentoService = commentoService;
		this.partitaService = partitaService;
		this.credenzialiService = credenzialiService;
	}
	
	@GetMapping("/utenti/partite/{id}/commenti/new")
	public String createForm(@PathVariable("id") Long id, Model model) {
		Commento commento = new Commento();
		commento.setPartita(this.partitaService.findById(id));
		model.addAttribute("commento", commento);
		return "utenti/commenti/form";
		
	}
	
	@PostMapping("/utenti/partite/{id}/commenti")
	public String save(@PathVariable("id") Long id, @Valid @ModelAttribute("commento") Commento commento, BindingResult bindingResult,
			Model model) {
		if(bindingResult.hasErrors()) {
			return "utenti/commenti/form";
		}
		UserDetails user = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(!(authentication instanceof AnonymousAuthenticationToken)) {
			user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		Credenziali credenziali = this.credenzialiService.findByUsername(user.getUsername());
		commento.setId(null);
		commento.setPartita(this.partitaService.findById(id));
		commento.setUtente(credenziali.getUtente());
		this.commentoService.save(commento);
		return "redirect:/partite/" + id;
		
	}
	
	@GetMapping("/utenti/commenti/{id}/edit")
	public String edit(@PathVariable("id") Long id, Model model) {
		Commento commento = this.commentoService.findById(id);
		Credenziali credUtente = this.credenzialiService.findByUtente(commento.getUtente());
		UserDetails user = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(!(authentication instanceof AnonymousAuthenticationToken)) {
			user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		if(user != null && credUtente.getUsername().equals(user.getUsername())) {
			model.addAttribute("commento", commento);
			return "utenti/commenti/form";
		}
		return "redirect:/partite/" + commento.getPartita().getId();

	}
	
	@PostMapping("/utenti/commenti/{id}")
	public String update(@PathVariable("id") Long id, @Valid @ModelAttribute Commento commento, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			return "utenti/commenti/form";
		}
		Commento commentoOriginale = this.commentoService.findById(id);
		commento.setId(id);
		commento.setPartita(commentoOriginale.getPartita());
		commento.setUtente(commentoOriginale.getUtente());
		this.commentoService.save(commento);
		return "redirect:/partite/" + commento.getPartita().getId();
		
	}
}
