package it.uniroma3.siw.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "squadre",uniqueConstraints = @UniqueConstraint(columnNames = {"nome","citta"}))
public class Squadra {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	

	@Column(nullable = false)
	private String nome;
	
	@NotNull
	@Min(1857)
	@Max(2026)
	@Column(nullable = false)
	private Integer annoFondazione;
	
	@NotBlank
	@Column(nullable = false)
	private String citta;
	
	@OneToMany(mappedBy = "squadra", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
	private List<Giocatore> giocatori = new ArrayList<>();
	
	@ManyToMany
	@JsonIgnore
	private List<Torneo> tornei = new ArrayList<>();
	
	@OneToMany(mappedBy = "squadraHome", cascade = CascadeType.REMOVE)
	private List<Partita> partiteInCasa = new ArrayList<>();

	@OneToMany(mappedBy = "squadraAway", cascade = CascadeType.REMOVE)
	private List<Partita> partiteInTrasferta = new ArrayList<>();
	
	public Squadra() {
		
	}

	public Squadra(String nome, Integer annoFondazione, String citta) {
		this.nome = nome;
		this.annoFondazione = annoFondazione;
		this.citta = citta;
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

	public Integer getAnnoFondazione() {
		return annoFondazione;
	}

	public void setAnnoFondazione(Integer annoFondazione) {
		this.annoFondazione = annoFondazione;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public List<Giocatore> getGiocatori() {
		return giocatori;
	}

	public void setGiocatori(List<Giocatore> giocatori) {
		this.giocatori = giocatori;
	}

	public List<Torneo> getTornei() {
		return tornei;
	}

	public void setTornei(List<Torneo> tornei) {
		this.tornei = tornei;
	}
	
	

	public List<Partita> getPartiteInCasa() {
		return partiteInCasa;
	}

	public void setPartiteInCasa(List<Partita> partiteInCasa) {
		this.partiteInCasa = partiteInCasa;
	}

	public List<Partita> getPartiteInTrasferta() {
		return partiteInTrasferta;
	}

	public void setPartiteInTrasferta(List<Partita> partiteInTrasferta) {
		this.partiteInTrasferta = partiteInTrasferta;
	}

	@Override
	public int hashCode() {
		return Objects.hash(annoFondazione, citta, nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Squadra other = (Squadra) obj;
		return Objects.equals(annoFondazione, other.annoFondazione) && Objects.equals(citta, other.citta)
				&& Objects.equals(nome, other.nome);
	}
	
	
	
	
}
