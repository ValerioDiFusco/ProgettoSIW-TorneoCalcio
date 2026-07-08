import {useState} from "react";
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  TextField,
  Button,
} from "@mui/material";
import { createSquadra, updateSquadra } from "../services/SquadraService";
import type { Squadra } from "../types";

interface Props {
  open: boolean;
  onClose: () => void; 
  onCreated: () => void; 
  squadraDaModificare?: Squadra | null;
}

export default function SquadraCreateDialog({ open, onClose, onCreated, squadraDaModificare }: Props) {
  const [nome, setNome] = useState(""); 
  const [annoFondazione, setAnnoFondazione] = useState<number | "">(""); 
  const [citta, setCitta] = useState(""); 
  


  const handleSubmit = async () => {

    const data = ({nome, annoFondazione: Number(annoFondazione), citta});
	try{
		if(squadraDaModificare){
			await updateSquadra(squadraDaModificare.id, data); 
		}
		else{
			await createSquadra(data);
		}
		
		resetForm();
		onCreated();
		onClose();
		
	}catch(error){
		console.error("Errore salvataggio",error);
	}

  };
  

  const resetForm = () => {
    setNome("");
    setAnnoFondazione("");
    setCitta("");
  };

  return (
    <Dialog open={open} onClose={onClose} fullWidth maxWidth="sm">
      <DialogTitle>{squadraDaModificare ? "Modifica Squadra" : "Nuova Squadra"}</DialogTitle>
      <DialogContent sx={{ display: "flex", flexDirection: "column", gap: 3, mt: 2 }}>
        <TextField
          label="Nome Squadra"
          value={nome}
          onChange={(e) => setNome(e.target.value)}
          required
        />
        <TextField
          label="Anno di Fondazione"
          type="number"
          value={annoFondazione}
          onChange={(e) => setAnnoFondazione(e.target.value ? Number(e.target.value) : "")}
          required
        />
        <TextField
          label="Città"
          value={citta}
          onChange={(e) => setCitta(e.target.value)}
          required
        />
      </DialogContent>
      <DialogActions>
        <Button onClick={onClose}>Annulla</Button>
		{/*disabled = pulsante non è cliccabile se l'utente non ha inserito tutti i dati, variant= gli da uno sfondo colorato*/}
        <Button variant="contained" onClick={handleSubmit} disabled={!nome || !annoFondazione || !citta}> 
          Salva
        </Button>
      </DialogActions>
    </Dialog>
  );
}