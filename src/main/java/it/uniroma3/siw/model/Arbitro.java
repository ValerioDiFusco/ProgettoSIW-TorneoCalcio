package it.uniroma3.siw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;


@Entity
// non favvio uniqueConstraint perchè nel codiceArbitro ho messo unique
public class Arbitro {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private String cognome;
	
	@Column(nullable = false, unique = true)
	private String codiceArbitro;
	
	@OneToMany(mappedBy = "arbitro", cascade = {CascadeType.PERSIST})
	private List<Partita> partite = new ArrayList<>();
	
	public Arbitro() {
		
	}

	public Arbitro(String nome, String cognome, String codiceArbitro) {
		this.nome = nome;
		this.cognome = cognome;
		this.codiceArbitro = codiceArbitro;
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

	public String getCodiceArbitro() {
		return codiceArbitro;
	}

	public void setCodiceArbitro(String codiceArbitro) {
		this.codiceArbitro = codiceArbitro;
	}

	public List<Partita> getPartite() {
		return partite;
	}

	public void setPartite(List<Partita> partite) {
		this.partite = partite;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codiceArbitro);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Arbitro other = (Arbitro) obj;
		return Objects.equals(codiceArbitro, other.codiceArbitro);
	}
	
	
	
	
}
