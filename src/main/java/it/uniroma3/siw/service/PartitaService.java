package it.uniroma3.siw.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	@Transactional(readOnly = true)
	public Partita findById(Long id) {
		Optional<Partita> optionalPartita = this.partitaRepository.findById(id);
		if(optionalPartita.isPresent()) {
			return optionalPartita.get();
		}
		else {
			throw new PartitaNotFoundException(id);
		}
	}
	
	
	@Transactional(readOnly = true)
	public List<Partita> findAll(){
		return (List<Partita>) this.partitaRepository.findAllByOrderByStatoAscDataOraAsc();
	}
	
	@Transactional
	public Partita save(Partita partita) {
		return this.partitaRepository.save(partita);
	}
	
	@Transactional
	public void delete(Long id) {
		this.partitaRepository.deleteById(id);
	}
}
