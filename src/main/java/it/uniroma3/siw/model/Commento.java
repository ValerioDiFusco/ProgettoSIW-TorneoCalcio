package it.uniroma3.siw.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Commento {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String titolo;
	
	private String descrizione;
	
	@ManyToOne
	private Utente utente;
	
	@ManyToOne
	private Partita partita;
	
	public Commento() {
		
	}

	public Commento(Long id, String titolo, String descrizione) {
		this.id = id;
		this.titolo = titolo;
		this.descrizione = descrizione;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public Partita getPartita() {
		return partita;
	}

	public void setPartita(Partita partita) {
		this.partita = partita;
	}

	@Override
	public int hashCode() {
		return Objects.hash(descrizione, partita, titolo, utente);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Commento other = (Commento) obj;
		return Objects.equals(descrizione, other.descrizione) && Objects.equals(partita, other.partita)
				&& Objects.equals(titolo, other.titolo) && Objects.equals(utente, other.utente);
	}
	
	
	
	
	
}
