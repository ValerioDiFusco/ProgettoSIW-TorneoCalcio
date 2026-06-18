package it.uniroma3.siw.exception;

public class PartitaNotFoundException extends RuntimeException{
	public PartitaNotFoundException(Long id) {
		super("La partita con id " + id + " non è stata trovata");
	}
}
