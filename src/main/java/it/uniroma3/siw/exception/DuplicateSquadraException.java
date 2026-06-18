package it.uniroma3.siw.exception;

public class DuplicateSquadraException extends RuntimeException{
	public DuplicateSquadraException(String nome, String citta) {
		super("La squadra " + nome + " della città " + citta + " è già presente nel sistema");
	}
}
