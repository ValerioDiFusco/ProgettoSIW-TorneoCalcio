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
  const [squadraModifica, setSquadraModifica] = useState<Squadra | null>(null);

  const loadSquadre = () => {
    getSquadre().then((res) => {
        if (res && Array.isArray(res.data)) {
          setSquadre(res.data);
        } else {
          console.warn("I dati ricevuti non sono un array:", res.data);
          setSquadre([]);
        }
      })
      .catch((err) => {
        console.error("Errore nel recupero delle squadre:", err);
        setSquadre([]); 
      });
  };
  
  const eliminaSquadra= (id:number) =>{
	deleteSquadra(id).then(() =>  {loadSquadre();}).catch((err) => {console.error("Errore durante l'eliminazione della squadra",err);})
  }
  
  const modificaSquadra =(squadra: Squadra) =>{
	setSquadraModifica(squadra);
	setDialogOpen(true);
  }
  const handleOpenCreate = () =>{
	setSquadraModifica(null);
	setDialogOpen(true);
  }
  
  const handleCloseDialog = () =>{
	setDialogOpen(false);
	setSquadraModifica(null);
  }

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
          onClick={handleOpenCreate}
        >
          Nuova Squadra
        </Button>
      </Box>

      <SquadraFilterGrid squadre={squadre} deleteSquadra={eliminaSquadra} modificaSquadra={modificaSquadra}/>

      <SquadraCreateDialog
        open={dialogOpen}
        onClose={handleCloseDialog}
        onCreated={loadSquadre} 
		squadraDaModificare={squadraModifica}
      />
    </Container>
  );
}