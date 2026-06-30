package it.uniroma3.siw.controller.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.service.SquadraService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/rest/squadre")
public class RestSquadraController {
	
	private final SquadraService squadraService;
	
	public RestSquadraController(SquadraService squadraService) {
		this.squadraService = squadraService;
	}
	
	@GetMapping
	public List<Squadra> list(){
		return this.squadraService.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Squadra> show(@PathVariable Long id){
		Squadra squadra = this.squadraService.findById(id);
		if(squadra == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(squadra);
	}
	
	@PostMapping //Permette a React di inviare i dati di una nuova squadra per salvarla nel database.
	public ResponseEntity<Squadra> create(@Valid @RequestBody Squadra squadra){
		Squadra saved = this.squadraService.save(squadra);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Squadra> update(@PathVariable Long id, @Valid @RequestBody Squadra squadra){
		Squadra s = this.squadraService.findById(id);
		if(s == null) {
			return ResponseEntity.notFound().build();
		}
		squadra.setId(id);
		this.squadraService.update(squadra);
		return ResponseEntity.ok(squadra);
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete (@PathVariable Long id){
		this.squadraService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
