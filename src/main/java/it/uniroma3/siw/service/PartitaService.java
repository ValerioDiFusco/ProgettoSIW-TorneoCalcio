package it.uniroma3.siw.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import it.uniroma3.siw.exception.PartitaNotFoundException;
import it.uniroma3.siw.model.Arbitro;
import it.uniroma3.siw.model.Partita;
import it.uniroma3.siw.model.Stato;
import it.uniroma3.siw.model.Torneo;
import it.uniroma3.siw.repository.PartitaRepository;

@Service
public class PartitaService {
	
	private PartitaRepository partitaRepository;
	
	public PartitaService(PartitaRepository partitaRepository) {
		this.partitaRepository = partitaRepository;
	}
	
	public Partita findById(Long id) {
		Optional<Partita> optionalPartita = this.partitaRepository.findById(id);
		if(optionalPartita.isPresent()) {
			return optionalPartita.get();
		}
		else {
			throw new PartitaNotFoundException(id);
		}
	}
	
	public List<Partita> findGiocateByTorneo(Torneo torneo){
		return this.partitaRepository.findByTorneoAndStato(torneo, Stato.PLAYED);
	}
	
	public List<Partita> findProgrammateByTorneo(Torneo torneo){
		return this.partitaRepository.findByTorneoAndStato(torneo, Stato.SCHEDULED);
	}
	
	public List<Partita> findArbitrate(Arbitro arbitro){
		return this.partitaRepository.findByArbitroAndStato(arbitro, Stato.PLAYED);
	}
	public List<Partita> findDaArbitrare(Arbitro arbitro){
		return this.partitaRepository.findByArbitroAndStato(arbitro, Stato.SCHEDULED);
	}
	
	public List<Partita> findAll(){
		return (List<Partita>) this.partitaRepository.findAllByOrderByStatoAscDataOraAsc();
	}
	
	public Partita save(Partita partita) {
		return this.partitaRepository.save(partita);
	}
	
	public void delete(Long id) {
		this.partitaRepository.deleteById(id);
	}
}
