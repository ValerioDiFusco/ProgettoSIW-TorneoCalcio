package it.uniroma3.siw.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"data_ora", "torneo_id", "squadra_home_id", "squadra_away_id"}))
// da vedere uniqeuConstraint
public class Partita {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	

	@Column(nullable = false)
	private LocalDateTime dataOra;
	
	@Column(nullable = false)
	private String luogo;
	
	@Min(0)
	private Integer goalsHome;
	
	@Min(0)
	private Integer goalsAway;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Stato stato;
	

	@ManyToOne
	@NotNull
	@JsonIgnore
	private Arbitro arbitro;
	

	@ManyToOne
	@JsonIgnoreProperties("squadre")
	private Torneo torneo;

	@ManyToOne
	@JsonIgnore
	private Squadra squadraHome;
	
	@ManyToOne
	@JsonIgnore
	private Squadra squadraAway;
	
	@OneToMany(mappedBy = "partita")
	private List<Commento> commenti;
	
	public Partita() {
		
	}

	public Partita(LocalDateTime dataOra, String luogo, Integer goalsHome, Integer goalsAway,
			Stato stato) {
		this.dataOra = dataOra;
		this.luogo = luogo;
		this.goalsHome = goalsHome;
		this.goalsAway = goalsAway;
		this.stato = stato;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDataOra() {
		return dataOra;
	}

	public void setDataOra(LocalDateTime dataOra) {
		this.dataOra = dataOra;
	}

	public String getLuogo() {
		return luogo;
	}

	public void setLuogo(String luogo) {
		this.luogo = luogo;
	}

	public Integer getGoalsHome() {
		return goalsHome;
	}

	public void setGoalsHome(Integer goalsHome) {
		this.goalsHome = goalsHome;
	}

	public Integer getGoalsAway() {
		return goalsAway;
	}

	public void setGoalsAway(Integer goalsAway) {
		this.goalsAway = goalsAway;
	}

	public Stato getStato() {
		return stato;
	}

	public void setStato(Stato stato) {
		this.stato = stato;
	}

	public Arbitro getArbitro() {
		return arbitro;
	}

	public void setArbitro(Arbitro arbitro) {
		this.arbitro = arbitro;
	}

	public Torneo getTorneo() {
		return torneo;
	}

	public void setTorneo(Torneo torneo) {
		this.torneo = torneo;
	}

	public Squadra getSquadraHome() {
		return squadraHome;
	}

	public void setSquadraHome(Squadra squadraHome) {
		this.squadraHome = squadraHome;
	}

	public Squadra getSquadraAway() {
		return squadraAway;
	}

	public void setSquadraAway(Squadra squadraAway) {
		this.squadraAway = squadraAway;
	}

	public List<Commento> getCommenti() {
		return commenti;
	}

	public void setCommenti(List<Commento> commenti) {
		this.commenti = commenti;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dataOra, luogo);
	}
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Partita other = (Partita) obj;
		return Objects.equals(dataOra, other.dataOra) && Objects.equals(luogo, other.luogo);
	}
	
	
	
}
