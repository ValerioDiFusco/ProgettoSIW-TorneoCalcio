package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Commento;
import it.uniroma3.siw.service.CommentoService;
import it.uniroma3.siw.service.PartitaService;
import jakarta.validation.Valid;

@Controller
public class CommentoController {
	
	private CommentoService commentoService;
	private PartitaService partitaService;
	
	public CommentoController(CommentoService commentoService,PartitaService partitaService) {
		this.commentoService = commentoService;
		this.partitaService = partitaService;
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
		commento.setId(null);
		commento.setPartita(this.partitaService.findById(id));
		this.commentoService.save(commento);
		return "redirect:/partite/" + id;
		
	}
}
