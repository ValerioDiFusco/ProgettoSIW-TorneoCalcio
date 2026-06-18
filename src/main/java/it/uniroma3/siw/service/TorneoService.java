package it.uniroma3.siw.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import it.uniroma3.siw.exception.DuplicateTorneoException;
import it.uniroma3.siw.exception.TorneoNotFoundException;
import it.uniroma3.siw.model.Torneo;
import it.uniroma3.siw.repository.TorneoRepository;
import jakarta.transaction.Transactional;

@Service
public class TorneoService {
	
	private TorneoRepository torneoRepository;
	
	public TorneoService(TorneoRepository torneoRepository) {
		this.torneoRepository = torneoRepository;
	}
	
	public List<Torneo> getAllTornei(){
		return (List<Torneo>) this.torneoRepository.findAll();
	}
	
	public List<Torneo> filtraListaByNome(String nome){
		return (List<Torneo>) this.torneoRepository.findByNomeContainingIgnoreCase(nome);
	}
	
	public Torneo findById(Long id) {
		Optional<Torneo> optionalTorneo = this.torneoRepository.findById(id);
		if(optionalTorneo.isPresent()) {
			return optionalTorneo.get();
		}
		else {
			throw new TorneoNotFoundException(id);
		}
	}
	
	@Transactional
	public Torneo save(Torneo torneo) throws DuplicateTorneoException {
		if(this.torneoRepository.existsByNomeIgnoreCaseAndAnno(torneo.getNome(), torneo.getAnno())) {
			throw new DuplicateTorneoException(torneo.getNome(),torneo.getAnno());
		}
		return this.torneoRepository.save(torneo);
	}
	
	@Transactional
	public Torneo update(Torneo torneo) {
		return this.torneoRepository.save(torneo);
	}
	
	
}
