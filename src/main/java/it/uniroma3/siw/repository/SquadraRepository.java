package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.model.Torneo;

public interface SquadraRepository extends CrudRepository <Squadra,Long> {
	
	public boolean existsByNomeIgnoreCaseAndCittaIgnoreCase(String nome, String citta);
	
	public List<Squadra> findByTorneiId(Long id);
	
	public List<Squadra> findByTorneiNotContaining(Torneo torneo);
	
	
}
