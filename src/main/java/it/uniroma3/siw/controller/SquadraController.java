package it.uniroma3.siw.controller;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.exception.DuplicateGiocatoreException;
import it.uniroma3.siw.exception.DuplicateSquadraException;
import it.uniroma3.siw.model.Giocatore;
import it.uniroma3.siw.model.Ruolo;
import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.service.GiocatoreService;
import it.uniroma3.siw.service.SquadraService;
import jakarta.validation.Valid;

@Controller
public class SquadraController {
	
	private SquadraService squadraService;
	private GiocatoreService giocatoreService;
	
	public SquadraController(SquadraService squadraService,GiocatoreService giocatoreService) {
		this.squadraService = squadraService;
		this.giocatoreService = giocatoreService;
	}
	
	@GetMapping("/squadre/{id}")
	public String show(@PathVariable ("id") Long id,Model model) {
		Squadra s = this.squadraService.findById(id);
		List<Giocatore> giocatori = s.getGiocatori();
		giocatori.sort(Comparator.comparing(Giocatore::getRuolo));
		model.addAttribute("squadra", s);
		model.addAttribute("giocatori", giocatori);
		return "squadre/show";
	}
	
	@GetMapping("/squadre")
	public String list(Model model) {
		List<Squadra> squadre = this.squadraService.findAll();
		model.addAttribute("squadre", squadre);
		return "squadre/list";
	}
	
	@GetMapping("/admin/squadre/new")
	public String createForm(Model model) {
		model.addAttribute("squadra", new Squadra());
		return "admin/squadre/form";
	}
	
	@PostMapping("/admin/squadre")
	public String save(@Valid @ModelAttribute("squadra") Squadra squadra, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "admin/squadre/form";
		}
		try {
			this.squadraService.save(squadra);
			return "redirect:/squadre";
		}
		catch (DuplicateSquadraException e) {
			bindingResult.reject("squadra.duplicate");
			return "admin/squadre/form";
		}
	}

	
}
