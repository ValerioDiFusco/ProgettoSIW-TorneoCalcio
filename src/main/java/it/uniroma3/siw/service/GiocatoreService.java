package it.uniroma3.siw.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.exception.DuplicateGiocatoreException;
import it.uniroma3.siw.exception.GiocatoreNotFoundException;
import it.uniroma3.siw.model.Giocatore;
import it.uniroma3.siw.repository.GiocatoreRepository;


@Service
public class GiocatoreService {
	
	private GiocatoreRepository giocatoreRepository;
	
	public GiocatoreService(GiocatoreRepository giocatoreRepository) {
		this.giocatoreRepository = giocatoreRepository;
	}
	
	@Transactional(readOnly = true)
	public List<Giocatore> findAllByOrderByCognomeAsc(){
		return this.giocatoreRepository.findAllByOrderByCognomeAsc();
	}
	
	@Transactional(readOnly = true)
	public List<Giocatore> searchByNomeOrCognome(String parola){
		return this.giocatoreRepository.findByNomeContainingIgnoreCaseOrCognomeContainingIgnoreCase(parola, parola);
	}
	
	@Transactional(readOnly = true)
	public List<Giocatore> searchByNomeAndCognome(String nome,String cognome) {
		return this.giocatoreRepository.findByNomeContainingIgnoreCaseAndCognomeContainingIgnoreCase(nome, cognome);
	}
	
	@Transactional(readOnly = true)
	public Giocatore findById(Long id) {
		Optional<Giocatore> optionalGiocatore = this.giocatoreRepository.findById(id);
		if(optionalGiocatore.isPresent()) {
			return optionalGiocatore.get();
		}
		else {
			throw new GiocatoreNotFoundException(id);
		}
	}
	
	@Transactional
	public Giocatore save(Giocatore giocatore) throws DuplicateGiocatoreException { 
		if(this.giocatoreRepository.existsByCodiceFiscaleIgnoreCase(giocatore.getCodiceFiscale())) {
			throw new DuplicateGiocatoreException(giocatore.getCodiceFiscale());
		}
		return this.giocatoreRepository.save(giocatore);
	}
	

}
