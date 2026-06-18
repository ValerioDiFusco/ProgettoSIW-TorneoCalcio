package it.uniroma3.siw.exception;

public class SquadraNotFoundException extends RuntimeException {
	public SquadraNotFoundException(Long id) {
		super("Squadra con id "+ id +" non trovata");
	}
}
