package it.uniroma3.siw.exception;

public class CommentoNotFoundException extends RuntimeException {
	public CommentoNotFoundException(Long id) {
		super("Il commento con id "+ id +"non esiste");
	}
}
