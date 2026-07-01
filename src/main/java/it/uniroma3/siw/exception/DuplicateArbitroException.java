package it.uniroma3.siw.exception;

public class DuplicateArbitroException extends RuntimeException {
	public DuplicateArbitroException(String codiceArbitro) {
		super("L'arbitro con codice " + codiceArbitro + "esiste già");
	}
}
