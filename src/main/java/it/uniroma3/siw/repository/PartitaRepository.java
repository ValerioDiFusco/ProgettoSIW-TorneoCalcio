package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Arbitro;
import it.uniroma3.siw.model.Partita;
import it.uniroma3.siw.model.Stato;
import it.uniroma3.siw.model.Torneo;

public interface PartitaRepository extends CrudRepository<Partita,Long>{
	
	@EntityGraph(attributePaths = {"torneo", "squadraHome", "squadraAway"})
	public List<Partita> findAllByOrderByStatoAscDataOraAsc();
}
