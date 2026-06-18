package it.uniroma3.siw.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import it.uniroma3.siw.exception.DuplicateSquadraException;
import it.uniroma3.siw.exception.SquadraNotFoundException;
import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.model.Torneo;
import it.uniroma3.siw.repository.SquadraRepository;
import jakarta.transaction.Transactional;

@Service
public class SquadraService {
	
	private SquadraRepository squadraRepository;
	
	public SquadraService(SquadraRepository squadraRepository) {
		this.squadraRepository = squadraRepository;
	}
	
	public Long countByTornei(Torneo t){
		return this.squadraRepository.countByTornei(t);
	}
	
	public Squadra findById(Long id) {
		Optional<Squadra> optionalSquadra = this.squadraRepository.findById(id);
		if(optionalSquadra.isPresent()) {
			return optionalSquadra.get();
		}
		else {
			throw new SquadraNotFoundException(id);
		}
	}
	
	public List<Squadra> findAllById(List<Long> squadreId){
		return (List<Squadra>) this.squadraRepository.findAllById(squadreId);
	}
	
	public List<Squadra> findAll(){
		return (List<Squadra>) this.squadraRepository.findAll();
	}
	
	public List<Squadra> findByTorneiId(Long id){
		return this.squadraRepository.findByTorneiId(id);
	}
	
	@Transactional
	public Squadra save(Squadra squadra) throws DuplicateSquadraException {
		if(this.squadraRepository.existsByNomeIgnoreCaseAndCittaIgnoreCase(squadra.getNome(), squadra.getCitta())) {
			throw new DuplicateSquadraException(squadra.getNome(), squadra.getCitta());
		}
		return this.squadraRepository.save(squadra);
	}
}
