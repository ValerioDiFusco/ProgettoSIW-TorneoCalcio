package it.uniroma3.siw.exception;

public class TorneoNotFoundException extends RuntimeException{
	public TorneoNotFoundException(Long id) {
		super("Il Torneo con Id " + id +" non esiste");
	}
}

