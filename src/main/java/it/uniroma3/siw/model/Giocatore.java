package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name ="giocatori")
public class Giocatore {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	@Pattern(regexp="^.{4}$") // ^ non permette testo prima, 4 caratteri, $ non di più
	@Column(nullable = false , unique= true)
	private String codiceFiscale;
	
	@NotBlank
	@Column(nullable = false)
	private String nome;
	
	@NotBlank
	@Column(nullable = false)
	private String cognome;
	
	@NotNull
	private LocalDate dataNascita;
	
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private Ruolo ruolo;
	

	@DecimalMin("1.00")
	@DecimalMax("2.50")
	private Double altezza;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	@JsonIgnore
	private Squadra squadra;
	
	public Giocatore() {
		
	}
	
	
	public Giocatore(String nome, String cognome, LocalDate dataNascita, Ruolo ruolo,
			 Double altezza, String codiceFiscale) {
		this.nome = nome;
		this.cognome = cognome;
		this.dataNascita = dataNascita;
		this.ruolo = ruolo;
		this.altezza = altezza;
		this.codiceFiscale = codiceFiscale;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public LocalDate getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}

	public Ruolo getRuolo() {
		return ruolo;
	}

	public void setRuolo(Ruolo ruolo) {
		this.ruolo = ruolo;
	}

	public Double getAltezza() {
		return altezza;
	}

	public void setAltezza(Double altezza) {
		this.altezza = altezza;
	}

	public Squadra getSquadra() {
		return squadra;
	}

	public void setSquadra(Squadra squadra) {
		this.squadra = squadra;
	}
	


	public String getCodiceFiscale() {
		return codiceFiscale;
	}


	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}


	@Override
	public int hashCode() {
		return Objects.hash(cognome, dataNascita, nome);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Giocatore other = (Giocatore) obj;
		return Objects.equals(cognome, other.cognome) && Objects.equals(dataNascita, other.dataNascita)
				&& Objects.equals(nome, other.nome);
	}




	
	
	
}
