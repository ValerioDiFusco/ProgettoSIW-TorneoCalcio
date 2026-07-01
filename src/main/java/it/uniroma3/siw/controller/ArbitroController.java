package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Arbitro;
import it.uniroma3.siw.service.ArbitroService;
import it.uniroma3.siw.service.PartitaService;
import jakarta.validation.Valid;

@Controller
public class ArbitroController {
	
	private ArbitroService arbitroService;
	private PartitaService partitaService;
	
	public ArbitroController(ArbitroService arbitroService,PartitaService partitaService) {
		this.arbitroService = arbitroService;
		this.partitaService = partitaService;
	}
	
	@GetMapping("/arbitri/{id}")
	public String show(@PathVariable("id") Long id, Model model){
		Arbitro a = this.arbitroService.findById(id);
		model.addAttribute("arbitro",a);
		model.addAttribute("partiteArbitrate", this.partitaService.findArbitrate(a));
		model.addAttribute("partiteDaArbitrare", this.partitaService.findDaArbitrare(a));
		return "arbitri/show";
	}
	
	@GetMapping("/arbitri")
	public String list(Model model) {
		model.addAttribute("arbitri", this.arbitroService.findAll());
		return "arbitri/list";
	}
	
	@GetMapping("/admin/arbitri/new")
	public String createForm(Model model) {
		model.addAttribute("arbitro", new Arbitro());
		return "admin/arbitri/form";
	}
	
	@PostMapping("/admin/arbitri")
	public String save(@Valid @ModelAttribute Arbitro arbitro, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			return "admin/arbitri/form";
		}
		this.arbitroService.save(arbitro);
		return "redirect:/arbitri";
	}
}
