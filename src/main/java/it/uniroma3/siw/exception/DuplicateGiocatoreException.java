package it.uniroma3.siw.exception;

import java.time.LocalDate;

public class DuplicateGiocatoreException extends RuntimeException {
	public DuplicateGiocatoreException(String codiceFiscale) {
		super("Il giocatore con CF: " + codiceFiscale + " è già presente nel sistema");
	}
}
