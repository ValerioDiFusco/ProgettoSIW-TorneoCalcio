import api from "./api";
import type {Squadra} from "../types";

export function getSquadre(){
	return api.get<Squadra[]>("/rest/squadre");
}

export function getSquadraById(id: number){
	return api.get<Squadra>(`/rest/squadre/${id}`);
}

export function createSquadra(data:{
	nome: string;
	citta: string;
	annoFondazione: number;
}){
	return api.post<Squadra>("/rest/squadre",data);
}

export function updateSquadra(id: number, data:{
	nome: string;
	citta: string;
	annoFondazione: number;
}){
	return api.put<Squadra>(`/rest/squadre/${id}`,data);
}

export function deleteSquadra(id: number){
	return api.delete<void>(`/rest/squadre/${id}`);
}