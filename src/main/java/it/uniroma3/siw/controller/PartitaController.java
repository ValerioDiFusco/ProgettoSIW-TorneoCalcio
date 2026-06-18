package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Partita;
import it.uniroma3.siw.model.Stato;
import it.uniroma3.siw.service.ArbitroService;
import it.uniroma3.siw.service.CommentoService;
import it.uniroma3.siw.service.PartitaService;
import it.uniroma3.siw.service.SquadraService;
import it.uniroma3.siw.service.TorneoService;
import jakarta.validation.Valid;

@Controller
public class PartitaController {
	
	private PartitaService partitaService;
	private TorneoService torneoService;
	private SquadraService squadraService;
	private ArbitroService arbitroService;
	private CommentoService commentoService;
	

	
	public PartitaController(PartitaService partitaService, TorneoService torneoService, SquadraService squadraService,
			ArbitroService arbitroService,CommentoService commentoService) {
		this.partitaService = partitaService;
		this.torneoService = torneoService;
		this.squadraService = squadraService;
		this.arbitroService = arbitroService;
		this.commentoService = commentoService;
	}

	@GetMapping("/partite/{id}")
	public String show(@PathVariable("id") Long id, Model model) {
		Partita p = this.partitaService.findById(id);
		model.addAttribute("partita", p);
		model.addAttribute("arbitro",p.getArbitro());
		model.addAttribute("nCommenti", this.commentoService.countByPartitaId(id));
		return "partite/show";
	}
	
	@GetMapping("/partite")
	public String list(Model model) {
		model.addAttribute("partite", this.partitaService.findAll());
		return "partite/list";
	}
	
	@GetMapping("/tornei/{id}/partite/new")
	public String createForm(@PathVariable("id") Long id,Model model) {
		Partita partita = new Partita();
		partita.setTorneo(this.torneoService.findById(id));
		model.addAttribute("partita", partita);
		model.addAttribute("torneo", this.torneoService.findById(id));
		model.addAttribute("squadre",this.squadraService.findByTorneiId(id));
		model.addAttribute("arbitri",this.arbitroService.findAll());
		return "partite/form";
	}
	
	@PostMapping("/tornei/{id}/partite")
	public String save(@PathVariable("id") Long id,@Valid @ModelAttribute("partita") Partita partita, BindingResult bindingResult, Model model) {
		if(partita.getSquadraHome() == null || partita.getSquadraAway() == null) {
			bindingResult.reject("squadre.mancanti");
		}
		else if(partita.getSquadraHome().getId() != null && partita.getSquadraHome().getId().equals(partita.getSquadraAway().getId())){
			bindingResult.reject("squadre.uguali");
		}
		if(bindingResult.hasErrors()) {
			model.addAttribute("torneo", this.torneoService.findById(id));
			model.addAttribute("squadre", this.squadraService.findByTorneiId(id));
			model.addAttribute("arbitri", this.arbitroService.findAll());
			return "partite/form";
		}
		partita.setId(null);
		partita.setTorneo(this.torneoService.findById(id));
		partita.setStato(Stato.SCHEDULED);
		this.partitaService.save(partita);
		return "redirect:/tornei/" + id;
		
	}
	
	@GetMapping("/partite/{id}/addRisultato")
	public String createFormRisultato(@PathVariable("id") Long id,Model model) {
		model.addAttribute("partita", this.partitaService.findById(id));
		return "partite/risultato";
	}
	
	@PostMapping("/partite/{id}/risultato")
	public String saveResult(@PathVariable Long id,
	                         @ModelAttribute("partita") Partita partita,
	                         BindingResult bindingResult,Model model) {

	    Partita dbPartita = this.partitaService.findById(id);
	    
	    if(partita.getGoalsHome() == null || partita.getGoalsAway() == null) {
	    		bindingResult.reject("risultato.vuoto");
	    }
	    if(partita.getGoalsHome() != null && partita.getGoalsHome() < 0 ||
	    		   partita.getGoalsAway() != null && partita.getGoalsAway() < 0) {
	    		bindingResult.reject("risultato.negativo");
	    }
	    if(bindingResult.hasErrors()) {
	    		partita.setId(id);
		    partita.setSquadraHome(dbPartita.getSquadraHome());
	        partita.setSquadraAway(dbPartita.getSquadraAway());
	        return "partite/risultato";
	    }
	    
	    dbPartita.setGoalsHome(partita.getGoalsHome());
	    dbPartita.setGoalsAway(partita.getGoalsAway());
	    dbPartita.setStato(Stato.PLAYED);

	    this.partitaService.save(dbPartita);
	    

	    return "redirect:/partite/" + id;
	}
	
	
}
