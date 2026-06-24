package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Arbitro;
import it.uniroma3.siw.model.Partita;
import it.uniroma3.siw.model.Stato;
import it.uniroma3.siw.model.Torneo;

public interface PartitaRepository extends CrudRepository<Partita,Long>{
	
	public List<Partita> findByTorneoAndStato(Torneo torneo, Stato stato);
	
	public List<Partita> findByArbitroAndStato(Arbitro arbitro,Stato stato);
	
	@EntityGraph(attributePaths = {"arbitro", "torneo", "squadraHome", "squadraAway"})
	public List<Partita> findAllByOrderByStatoAscDataOraAsc();
}
