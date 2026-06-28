export const Ruolo ={
	PORTIERE:'PORTIERE',
	DIFENSORE: 'DIFENSORE',
	CENTROCAMPISTA: 'CENTROCAMPISTA',
	ATTACCANTE: 'ATTACCANTE'
}as const;

export type RuoloType = typeof Ruolo[keyof typeof Ruolo];

export interface Giocatore{
	id: number;
	codiceFiscale: string;
	nome: string;
	cognome: string;
	dataNascita: string;
	ruolo: RuoloType;
	altezza: number;
	squadra: Squadra;
}

export interface Squadra{
	id:number;
	nome: string;
	citta: string;
	annoFondazione: number;
	giocatori?: Giocatore[];
}