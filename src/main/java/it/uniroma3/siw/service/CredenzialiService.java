package it.uniroma3.siw.service;

import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.repository.CredenzialiRepository;

@Service
public class CredenzialiService {
	
	private CredenzialiRepository credenzialiRepository;
	
	public CredenzialiService(CredenzialiRepository credenzialiRepository) {
		this.credenzialiRepository = credenzialiRepository;
	}
	
	public Credenziali findById(Long id) {
		return this.credenzialiRepository.findById(id).get();
	}
	
	public Credenziali findByUsername(String username) {
		return this.credenzialiRepository.findByUsername(username).get();
	}
	
	public Credenziali save(Credenziali credenziali) {
		return this.credenzialiRepository.save(credenziali);
	}
}
