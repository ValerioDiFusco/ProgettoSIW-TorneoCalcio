package it.uniroma3.siw.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.exception.DuplicateSquadraException;
import it.uniroma3.siw.exception.SquadraNotFoundException;
import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.model.Torneo;
import it.uniroma3.siw.repository.SquadraRepository;


@Service
public class SquadraService {
	
	private SquadraRepository squadraRepository;
	
	public SquadraService(SquadraRepository squadraRepository) {
		this.squadraRepository = squadraRepository;
	}
	
	@Transactional(readOnly = true)
	public Long countByTornei(Torneo t){
		return this.squadraRepository.countByTornei(t);
	}
	
	@Transactional(readOnly = true)
	public Squadra findById(Long id) {
		Optional<Squadra> optionalSquadra = this.squadraRepository.findById(id);
		if(optionalSquadra.isPresent()) {
			return optionalSquadra.get();
		}
		else {
			throw new SquadraNotFoundException(id);
		}
	}
	
	@Transactional(readOnly = true)
	public List<Squadra> findAllById(List<Long> squadreId){
		return (List<Squadra>) this.squadraRepository.findAllById(squadreId);
	}
	
	@Transactional(readOnly = true)
	public List<Squadra> findAll(){
		return (List<Squadra>) this.squadraRepository.findAll();
	}
	
	@Transactional(readOnly = true)
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
	
	@Transactional
	public Squadra update(Squadra squadra) {
		return this.squadraRepository.save(squadra);
	}
	
	@Transactional
	public void delete(Long id) {
		this.squadraRepository.deleteById(id);
	}
}
