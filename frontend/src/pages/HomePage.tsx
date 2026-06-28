import { useEffect, useState } from "react";
import { Box, Button, Container, Typography } from "@mui/material";
import AddIcon from "@mui/icons-material/Add";
import SquadraCreateDialog from "../componenti/SquadraCreateDialog";
import SquadraFilterGrid from "../componenti/SquadraFilterGrid";
import type { Squadra } from "../types";
import { getSquadre, deleteSquadra } from "../services/SquadraService";

export default function HomePage() {
  const [squadre, setSquadre] = useState<Squadra[]>([]);
  const [dialogOpen, setDialogOpen] = useState(false);

  // Carica le squadre dal backend Spring Boot
  const loadSquadre = () => {
    getSquadre().then((res) => {
        // Controlliamo se res.data esiste ed è effettivamente un Array
        if (res && Array.isArray(res.data)) {
          setSquadre(res.data);
        } else {
          // Se non è un array (es. database vuoto o formato strano), salviamo un array vuoto
          console.warn("I dati ricevuti non sono un array:", res.data);
          setSquadre([]);
        }
      })
      .catch((err) => {
        console.error("Errore nel recupero delle squadre:", err);
        setSquadre([]); // In caso di errore di rete, evitiamo il crash passando un array vuoto
      });
  };
  
  const eliminaSquadra= (id:number) =>{
	deleteSquadra(id).then(() =>  {loadSquadre();}).catch((err) => {console.error("Errore durante l'eliminazione della squadra",err);})
  }

  // Esegue la chiamata al caricamento della pagina
  useEffect(() => {
    loadSquadre();
  }, []);

  return (
    <Container maxWidth="lg" sx={{ py: 4 }}>
      <Box sx={{ display: "flex", justifyContent: "space-between", alignItems: "center", mb: 3 }}>
        <Typography variant="h4">🛡️ Torneo SIW</Typography>
        <Button 
          variant="contained" 
          startIcon={<AddIcon />} 
          onClick={() => setDialogOpen(true)}
        >
          Nuova Squadra
        </Button>
      </Box>

      {/* La griglia filtrabile delle squadre */}
      <SquadraFilterGrid squadre={squadre} deleteSquadra={eliminaSquadra} />

      {/* Il Dialog di creazione che abbiamo fatto nel passaggio precedente */}
      <SquadraCreateDialog
        open={dialogOpen}
        onClose={() => setDialogOpen(false)}
        onCreated={loadSquadre} // Quando crea, riesegue loadSquadre agganciando i nuovi dati
      />
    </Container>
  );
}