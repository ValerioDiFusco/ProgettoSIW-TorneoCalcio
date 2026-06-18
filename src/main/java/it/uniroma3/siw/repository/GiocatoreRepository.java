package it.uniroma3.siw.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Giocatore;

public interface GiocatoreRepository extends CrudRepository<Giocatore,Long>{
	
	public List<Giocatore> findAllByOrderByCognomeAsc();
	
	public List<Giocatore> findByNomeContainingIgnoreCaseOrCognomeContainingIgnoreCase(String nome, String cognome);
	public List<Giocatore> findByNomeContainingIgnoreCaseAndCognomeContainingIgnoreCase(String nome,String cognome);
	
	public boolean existsByCodiceFiscaleIgnoreCase(String codiceFiscale);

}
