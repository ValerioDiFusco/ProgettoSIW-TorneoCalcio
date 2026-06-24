package it.uniroma3.siw.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.exception.DuplicateTorneoException;
import it.uniroma3.siw.exception.TorneoNotFoundException;
import it.uniroma3.siw.model.Classifica;
import it.uniroma3.siw.model.Partita;
import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.model.Stato;
import it.uniroma3.siw.model.Torneo;
import it.uniroma3.siw.repository.TorneoRepository;


@Service
public class TorneoService {
	
	private TorneoRepository torneoRepository;
	
	public TorneoService(TorneoRepository torneoRepository) {
		this.torneoRepository = torneoRepository;
	}
	
	@Transactional(readOnly = true)
	public List<Torneo> getAllTornei(){
		return (List<Torneo>) this.torneoRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<Torneo> filtraListaByNome(String nome){
		return (List<Torneo>) this.torneoRepository.findByNomeContainingIgnoreCase(nome);
	}
	
	@Transactional(readOnly = true)
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
	
	@Transactional(readOnly = true)
	public Map<Squadra,Classifica> aggiornaClassifica(Torneo torneo) {
		Map<Squadra,Classifica> classifica = new  HashMap<>();
		
		for(Squadra s : torneo.getSquadre()) {
			classifica.put(s, new Classifica(s));
		}
		
		for(Partita p : torneo.getPartite()) {
			if(p.getStato()!= null && p.getStato() == Stato.PLAYED) {
				Classifica squadraHome = classifica.get(p.getSquadraHome());
				Classifica squadraAway = classifica.get(p.getSquadraAway());
				
				if(p.getGoalsHome() > p.getGoalsAway()) {
					squadraHome.incrementaVinte();
					squadraAway.incrementaPerse();
				}
				else if(p.getGoalsHome() < p.getGoalsAway()) {
					squadraHome.incrementaPerse();
					squadraAway.incrementaVinte();
				}
				else {
					squadraHome.incrementaPareggiate();
					squadraAway.incrementaPareggiate();
				}
			}
	
		}
		List<Classifica> classificaOrdinataList = new ArrayList<>(classifica.values());	
		Collections.sort(classificaOrdinataList);
		Map<Squadra, Classifica> classificaOrdinata = new LinkedHashMap<>();
		for(Classifica c : classificaOrdinataList) {
			classificaOrdinata.put(c.getSquadra(), c);
		}
		return classificaOrdinata;
		
	}

	
}
