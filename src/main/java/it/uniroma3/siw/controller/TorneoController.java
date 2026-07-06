package it.uniroma3.siw.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.exception.DuplicateTorneoException;
import it.uniroma3.siw.model.Classifica;
import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.model.Torneo;
import it.uniroma3.siw.service.PartitaService;
import it.uniroma3.siw.service.SquadraService;
import it.uniroma3.siw.service.TorneoService;
import jakarta.validation.Valid;

@Controller
public class TorneoController {
	
	private TorneoService torneoService;
	private SquadraService squadraService;
	private PartitaService partitaService;
	
	public TorneoController(TorneoService torneoService,SquadraService squadraService,PartitaService partitaService) {
		this.torneoService = torneoService;
		this.squadraService = squadraService;
		this.partitaService = partitaService;
	}
	
	@GetMapping("/tornei")
	public String list(@RequestParam(required = false) String nome, Model model) {
		if(nome != null && !nome.isBlank()) {
			model.addAttribute("listaTornei", this.torneoService.filtraListaByNome(nome));
		}
		else {
			model.addAttribute("listaTornei", this.torneoService.getAllTornei());
		}
		return "tornei/list";
	}
	
	@GetMapping("/tornei/{id}")
	public String show(@PathVariable("id") Long id, Model model) {
		Torneo t = this.torneoService.findById(id);
		Map<Squadra, Classifica> mappaClassifica = this.torneoService.aggiornaClassifica(t);
		model.addAttribute("numSquadre", mappaClassifica.size());
		model.addAttribute("torneo", t);
		model.addAttribute("partite", t.getPartite());
		model.addAttribute("classifica", mappaClassifica);
		return "tornei/show";
	}
	
	@GetMapping("/tornei/{id}/squadre")
	public String listSquadre(@PathVariable("id") Long id, Model model) {
		Torneo t = this.torneoService.findById(id);
		model.addAttribute("listaSquadre", t.getSquadre());
		return "tornei/listSquadre";
	}

	@GetMapping("/admin/tornei/{id}/squadre/add")
	public String addSquadra(@PathVariable("id") Long id,Model model) {
		Torneo t = this.torneoService.findById(id);
		List<Squadra> squadreDaIscrivere = this.squadraService.findByTorneiNotContaining(t);
		model.addAttribute("torneo", t);
		model.addAttribute("squadre", squadreDaIscrivere);
		return "admin/tornei/addSquadra";
	}
	
	//da rivedere
	@PostMapping("/admin/tornei/{id}/squadre")
	public String addSquadra(@PathVariable Long id, @RequestParam(required = false) List<Long> squadreId) { //metto la lista di Long perchè psso far sicrivere più sqadre
		if(squadreId == null) {
			return "redirect:/tornei/" + id;
		}
		Torneo t = this.torneoService.findById(id);
		List<Squadra> squadre = this.squadraService.findAllById(squadreId);
		t.getSquadre().addAll(squadre);
		for(Squadra s :squadre) {
			s.getTornei().add(t);
		}
		this.torneoService.update(t);
		return "redirect:/tornei/" + id;
	}

	@GetMapping("/admin/tornei/new")
	public String createForm(Model model) {
		model.addAttribute("torneo", new Torneo());
		return "admin/tornei/form";
	}
	
	@PostMapping("/admin/tornei")
	public String save(@Valid @ModelAttribute("torneo") Torneo torneo, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "admin/tornei/form";
		}
		try {
			this.torneoService.save(torneo);
			return "redirect:/tornei";
		}
		catch (DuplicateTorneoException e) {
			bindingResult.reject("torneo.duplicate");
			return "admin/tornei/form";
		}
	}
	
	@GetMapping("/admin/tornei/{id}/edit")
	public String edit(@PathVariable("id") Long id, Model model) {
		Torneo torneo = this.torneoService.findById(id);
		model.addAttribute("torneo", torneo);
		return "admin/tornei/form";
	}
	
	@PostMapping("/admin/tornei/{id}")
	public String update(@PathVariable("id") Long id, @Valid @ModelAttribute ("torneo") Torneo torneo, BindingResult bindingResult,
			Model model) {
		if(bindingResult.hasErrors()) {
			return "admin/tornei/form";
		}
		try {
			torneo.setId(id);
			this.torneoService.save(torneo);
			return "redirect:/tornei/" + id;
		}
		catch (DuplicateTorneoException e) {
			bindingResult.reject("torneo.duplicate");
			return "admin/tornei/form";
		}
	}
}
