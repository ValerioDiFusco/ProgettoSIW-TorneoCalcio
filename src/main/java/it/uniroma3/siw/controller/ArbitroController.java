package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.model.Arbitro;
import it.uniroma3.siw.service.ArbitroService;
import it.uniroma3.siw.service.PartitaService;

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
}
