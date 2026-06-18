package it.uniroma3.siw.exception;

public class ArbitroNotFoundException extends RuntimeException{
	public ArbitroNotFoundException(Long id) {
		super("Arbitro con id "+ id + " non trovato");
	}
}
