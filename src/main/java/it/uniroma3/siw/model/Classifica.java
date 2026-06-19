package it.uniroma3.siw.model;

public class Classifica implements Comparable<Classifica> {
	private Squadra squadra;
	
	private int giocate;
	
	private int vinte;
	
	private int perse;
	
	private int pareggiate;
	
	private int punti;
	
	
	public Classifica(Squadra squadra) {
		this.squadra = squadra;
		this.giocate = 0;
		this.vinte=0;
		this.perse = 0;
		this.pareggiate = 0;
		this.punti = 0;
	}
	

	public Squadra getSquadra() {
		return squadra;
	}


	public void setSquadra(Squadra squadra) {
		this.squadra = squadra;
	}

	public int getGiocate() {
		return giocate;
	}

	public void setGiocate(int giocate) {
		this.giocate = giocate;
	}

	public int getVinte() {
		return vinte;
	}

	public void setVinte(int vinte) {
		this.vinte = vinte;
	}

	public int getPerse() {
		return perse;
	}

	public void setPerse(int perse) {
		this.perse = perse;
	}

	public int getPareggiate() {
		return pareggiate;
	}

	public void setPareggiate(int pareggiate) {
		this.pareggiate = pareggiate;
	}

	public int getPunti() {
		return punti;
	}

	public void setPunti(int punti) {
		this.punti = punti;
	}
	
	
	public void incrementaVinte() {
		this.vinte++;
		this.giocate++;
		this.punti += 3;
	}
	
	public void incrementaPareggiate() {
		this.pareggiate++;
		this.giocate++;
		this.punti += 1;
	}
	
	public void incrementaPerse() {
		this.perse++;
		this.giocate++;
	}
	
	@Override
	public int compareTo(Classifica c) {
		return Integer.compare(c.getPunti(),this.punti);
	}
	

}
