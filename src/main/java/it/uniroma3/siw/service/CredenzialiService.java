package it.uniroma3.siw.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.CredenzialiRepository;

@Service
public class CredenzialiService {
	
	private CredenzialiRepository credenzialiRepository;
	
	public CredenzialiService(CredenzialiRepository credenzialiRepository) {
		this.credenzialiRepository = credenzialiRepository;
	}
	
	@Transactional(readOnly = true)
	public Credenziali findById(Long id) {
		return this.credenzialiRepository.findById(id).get();
	}
	
	@Transactional(readOnly = true)
	public Credenziali findByUsername(String username) {
		return this.credenzialiRepository.findByUsername(username).get();
	}
	
	@Transactional(readOnly = true)
	public Credenziali findByUtente(Utente utente) {
		return this.credenzialiRepository.findByUtente(utente);
	}
	
	@Transactional
	public Credenziali save(Credenziali credenziali) {
		return this.credenzialiRepository.save(credenziali);
	}
	
}
