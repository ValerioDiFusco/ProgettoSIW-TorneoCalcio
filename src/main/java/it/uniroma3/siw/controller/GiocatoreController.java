package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.xml.XmlValidationModeDetector;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.exception.DuplicateGiocatoreException;
import it.uniroma3.siw.model.Giocatore;
import it.uniroma3.siw.model.Ruolo;
import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.service.GiocatoreService;
import it.uniroma3.siw.service.SquadraService;
import jakarta.validation.Valid;

@Controller
public class GiocatoreController {
	
	private GiocatoreService giocatoreService;
	private SquadraService squadraService;
	
	public GiocatoreController(GiocatoreService giocatoreService,SquadraService squadraService) {
		this.giocatoreService = giocatoreService;
		this.squadraService = squadraService;
	}
	
	@GetMapping("/giocatori")
	public String list(@RequestParam(required = false) String nome,@RequestParam(required = false) String cognome,Model model) {
		List<Giocatore> giocatori;
		if(nome != null && !nome.isBlank() && cognome != null && !cognome.isBlank()) {
			giocatori = this.giocatoreService.searchByNomeAndCognome(nome,cognome);
		}
		else if(nome != null && !nome.isBlank()){
			giocatori = this.giocatoreService.searchByNomeOrCognome(nome);
		}
		else if(cognome != null && !cognome.isBlank()){
			giocatori = this.giocatoreService.searchByNomeOrCognome(cognome);
		}
		else {
			giocatori = this.giocatoreService.findAllByOrderByCognomeAsc();
		}
		Integer numGiocatori = giocatori.size();
		model.addAttribute("giocatori", giocatori);
		model.addAttribute("numGio", numGiocatori);
		return "giocatori/list";
	}
	
	@GetMapping("/giocatori/{id}")
	public String show(@PathVariable("id")Long id, Model model) {
		Giocatore giocatore = this.giocatoreService.findById(id);
		model.addAttribute("giocatore", giocatore);
		return "giocatori/show";
	}
	
	@GetMapping("/admin/squadre/{id}/giocatori/new")
	public String createForm(@PathVariable("id") Long id, Model model) {
		Giocatore giocatore = new Giocatore();
		giocatore.setSquadra(this.squadraService.findById(id));
		model.addAttribute("giocatore", giocatore);
		model.addAttribute("ruoli", Ruolo.values());
		return "admin/giocatori/form";
	}
	
	@PostMapping("/admin/squadre/{id}/giocatori")
	public String save(@PathVariable("id") Long id,@Valid @ModelAttribute("giocatore") Giocatore giocatore, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("ruoli", Ruolo.values());
			return "admin/giocatori/form";
		}
		try {
			giocatore.setSquadra(this.squadraService.findById(id));
			this.giocatoreService.save(giocatore);
			return "redirect:/squadre/" + id;
		}
		catch(DuplicateGiocatoreException e) {
			bindingResult.reject("giocatore.duplicate");
			model.addAttribute("ruoli", Ruolo.values());
			return "admin/giocatori/form";
		}
	}
	
	@GetMapping("/admin/giocatori/{id}/edit")
	public String edit(@PathVariable Long id,Model model) {
		Giocatore giocatore = this.giocatoreService.findById(id);
		model.addAttribute("giocatore", giocatore);
		model.addAttribute("ruoli", Ruolo.values());
		model.addAttribute("squadre", this.squadraService.findAll());
		return "admin/giocatori/form";
	}
	
	@PostMapping("/admin/giocatori/{id}")
	public String update(@PathVariable("id") Long id, @Valid @ModelAttribute("giocatore") Giocatore giocatore,
			BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("ruoli", Ruolo.values());
			model.addAttribute("squadre", this.squadraService.findAll());
			return "admin/giocatori/form";
		}
		else {
			giocatore.setId(id);
			this.giocatoreService.update(giocatore);
			return "redirect:/giocatori/" + id;
		}
	}

	

}
