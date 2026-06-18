package it.uniroma3.siw.exception;

public class GiocatoreNotFoundException extends RuntimeException {
	public GiocatoreNotFoundException(Long id) {
		super("Il giocatore con id "+ id + " non esiste");
	}
}
