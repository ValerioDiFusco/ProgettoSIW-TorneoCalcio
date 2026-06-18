package it.uniroma3.siw.exception;

public class DuplicateTorneoException extends RuntimeException{
	public DuplicateTorneoException(String nome, Integer year) {
		super("Il Torneo con " + nome + " e anno " + year + " esiste già nel sistema");
	}
}
