package it.uniroma3.siw.service;

import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.UtenteRepository;

@Service
public class UtenteService {
	
	private UtenteRepository utenteRepository;
	
	public UtenteService(UtenteRepository utenteRepository) {
		this.utenteRepository = utenteRepository;
	}
	
	public Utente findById(Long id) {
		return this.utenteRepository.findById(id).get();
	}
	
	public Utente save(Utente utente) {
		return this.utenteRepository.save(utente);
	}
}
