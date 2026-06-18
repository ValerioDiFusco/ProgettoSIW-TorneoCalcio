package it.uniroma3.siw.exception;

public class TorneoNotFoundException extends RuntimeException{
	public TorneoNotFoundException(Long id) {
		super("Il torneo con Id " + id +" non esiste");
	}
}

