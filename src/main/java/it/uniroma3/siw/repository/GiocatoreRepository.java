package it.uniroma3.siw.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Giocatore;

public interface GiocatoreRepository extends CrudRepository<Giocatore,Long>{
	
	@EntityGraph(attributePaths ={"squadra"})
	public List<Giocatore> findAllByOrderByCognomeAsc();
	
	@EntityGraph(attributePaths = {"squadra"})
    public List<Giocatore> findByNomeContainingIgnoreCase(String nome);
	
	@EntityGraph(attributePaths = {"squadra"})
	public List<Giocatore> findByCognomeContainingIgnoreCase(String cognome);
	
	@EntityGraph(attributePaths ={"squadra"})
	public List<Giocatore> findByNomeContainingIgnoreCaseAndCognomeContainingIgnoreCase(String nome,String cognome);
	
	public boolean existsByCodiceFiscaleIgnoreCase(String codiceFiscale);

}
