package it.uniroma3.siw.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.exception.CommentoNotFoundException;
import it.uniroma3.siw.exception.DuplicateGiocatoreException;
import it.uniroma3.siw.model.Commento;
import it.uniroma3.siw.repository.CommentoRepository;

@Service
public class CommentoService {
	
	private CommentoRepository commentoRepository;
	
	public CommentoService(CommentoRepository commentoRepository) {
		this.commentoRepository = commentoRepository;
	}
	
	@Transactional(readOnly = true)
	public Commento findById(Long id) {
		Optional<Commento> optionalCommento = this.commentoRepository.findById(id);
		if(optionalCommento.isPresent()) {
			return optionalCommento.get();					
		}
		else {
			throw new CommentoNotFoundException(id);
		}
	}
	
	@Transactional(readOnly = true)
	public List<Commento> findAll(){
		return (List<Commento>) this.commentoRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public int countByPartitaId(Long id) {
		return this.commentoRepository.countByPartitaId(id);
	}
	
	@Transactional
	public Commento save(Commento commento) {
		return this.commentoRepository.save(commento);
	}
}
