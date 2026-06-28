import { useState } from "react";
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  TextField,
  Button,
} from "@mui/material";
import { createSquadra } from "../services/SquadraService";

interface Props {
  open: boolean;
  onClose: () => void; //funzione per chiudere finistrea es quando premo annulla
  onCreated: () => void; //creazione squadra con successo aggiorna la lista
}

export default function SquadraCreateDialog({ open, onClose, onCreated }: Props) {
  const [nome, setNome] = useState(""); //nome parte con la string vuota
  const [annoFondazione, setAnnoFondazione] = useState<number | "">(""); //è u numero o una stringa vuota
  const [citta, setCitta] = useState(""); //citta parte con la string vuota

  const handleSubmit = async () => {
    // Chiamata alla funzione del tuo servizio che mappa la POST /rest/squadre
    await createSquadra({
      nome,
      annoFondazione: Number(annoFondazione),
      citta,
    });
    resetForm();
    onCreated();
    onClose();
  };

  const resetForm = () => {
    setNome("");
    setAnnoFondazione("");
    setCitta("");
  };

  return (
    <Dialog open={open} onClose={onClose} fullWidth maxWidth="sm">
      <DialogTitle>Nuova Squadra</DialogTitle>
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
          Crea
        </Button>
      </DialogActions>
    </Dialog>
  );
}