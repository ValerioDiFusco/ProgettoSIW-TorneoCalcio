package it.uniroma3.siw.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.exception.ArbitroNotFoundException;
import it.uniroma3.siw.model.Arbitro;
import it.uniroma3.siw.repository.ArbitroRepository;

@Service
public class ArbitroService {
	
	private ArbitroRepository arbitroRepository;
	
	public ArbitroService(ArbitroRepository arbitroRepository) {
		this.arbitroRepository = arbitroRepository;
	}
	
	@Transactional(readOnly = true)
	public Arbitro findById(Long id) {
		Optional<Arbitro> optionalArbitro = this.arbitroRepository.findById(id);
		if(optionalArbitro.isPresent()) {
			return optionalArbitro.get();
		}
		else {
			throw new ArbitroNotFoundException(id);
		}
	}
	
	@Transactional(readOnly = true)
	public List<Arbitro> findAll(){
		return (List<Arbitro>) this.arbitroRepository.findAll();
	}
	
	@Transactional
	public Arbitro save(Arbitro a) {
		return this.arbitroRepository.save(a);
	}
}
