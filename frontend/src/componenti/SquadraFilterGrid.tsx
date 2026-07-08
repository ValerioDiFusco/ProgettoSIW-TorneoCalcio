import { useMemo, useState } from "react";
import {
  Box,
  Card,
  CardContent,
  Chip,
  FormControl,
  InputLabel,
  MenuItem,
  Select,
  Slider,
  Typography,
  Grid,
  IconButton,
} from "@mui/material";
import type { Squadra } from "../types";


type SortOption = "nome-asc" | "nome-desc" | "anno-asc" | "anno-desc";

interface Props {
  squadre: Squadra[];
  deleteSquadra: (id:number) => void;
  modificaSquadra: (squadra: Squadra) => void;
}

export default function SquadraFilterGrid({ squadre, deleteSquadra, modificaSquadra }: Props) {

  const [selectedCitta, setSelectedCitta] = useState<string | null>(null);
  const [yearRange, setYearRange] = useState<[number, number] | null>(null);
  const [sort, setSort] = useState<SortOption>("nome-asc");

  
  const cittaElenco = useMemo(() => {
    const set = new Set<string>();
    squadre.forEach((s) => {
      if (s.citta) set.add(s.citta);
    });
    return Array.from(set).sort();
  }, [squadre]);


  const [minYear, maxYear] = useMemo(() => {
    if (squadre.length === 0) return [1857, 2026];
    const years = squadre.map((s) => s.annoFondazione);
    return [Math.min(...years), Math.max(...years)];
  }, [squadre]);

  const effectiveRange = useMemo(
    () => yearRange ?? [minYear, maxYear],
    [yearRange, minYear, maxYear]
  );


  const filtered = useMemo(() => {
    let result = squadre;

 
    if (selectedCitta) {
      result = result.filter((s) => s.citta === selectedCitta);
    }


    result = result.filter(
      (s) => s.annoFondazione >= effectiveRange[0] && s.annoFondazione <= effectiveRange[1]
    );


    result = [...result].sort((a, b) => {
      switch (sort) {
        case "nome-asc":
          return a.nome.localeCompare(b.nome);
        case "nome-desc":
          return b.nome.localeCompare(a.nome);
        case "anno-asc":
          return a.annoFondazione - b.annoFondazione;
        case "anno-desc":
          return b.annoFondazione - a.annoFondazione;
      }
    });

    return result;
  }, [squadre, selectedCitta, effectiveRange, sort]);

  return (
    <Box>
      
      <Box sx={{ mb: 3, display: "flex", flexDirection: "column", gap: 2 }}>
        
        
        <Box sx={{ display: "flex", flexWrap: "wrap", gap: 1 }}>
          <Chip
            label="Tutte le città"
            variant={selectedCitta === null ? "filled" : "outlined"}
            onClick={() => setSelectedCitta(null)}
          />
          {cittaElenco.map((c) => (
            <Chip
              key={c}
              label={c}
              variant={selectedCitta === c ? "filled" : "outlined"}
              onClick={() => setSelectedCitta(c)}
            />
          ))}
        </Box>

        
        <Box sx={{ display: "flex", gap: 4, alignItems: "center", flexWrap: "wrap" }}>
          <Box sx={{ minWidth: 200, flex: 1 }}>
            <Typography variant="body2" gutterBottom>
              Anno Fondazione: {effectiveRange[0]} — {effectiveRange[1]}
            </Typography>
            <Slider
              value={effectiveRange}
              onChange={(_, v) => setYearRange(v as [number, number])}
              min={minYear}
              max={maxYear}
              valueLabelDisplay="auto"
            />
          </Box>

          <FormControl size="small" sx={{ minWidth: 180 }}>
            <InputLabel>Ordinamento</InputLabel>
            <Select
              value={sort}
              label="Ordinamento"
              onChange={(e) => setSort(e.target.value as SortOption)}
            >
              <MenuItem value="nome-asc">Nome A→Z</MenuItem>
              <MenuItem value="nome-desc">Nome Z→A</MenuItem>
              <MenuItem value="anno-asc">Anno crescente</MenuItem>
              <MenuItem value="anno-desc">Anno decrescente</MenuItem>
            </Select>
          </FormControl>
        </Box>
      </Box>

      
      <Grid container spacing={2}>
        {filtered.map((squadra) => (
          <Grid size={{ xs: 12, sm: 6, md: 4 }} key={squadra.id}>
            <Card variant="outlined" sx={{ position:"relative",'&:hover': { boxShadow: 3 } }}>
			<IconButton size ="small" onClick={() => modificaSquadra(squadra)}
			sx={{
				position:"absolute",	
				top:40,
				right:8,
                 color: "#0288d1",
                 '&:hover': { backgroundColor: "#e1f5fe" }
            }}>
				✏️
			</IconButton>
			<IconButton size="small" onClick={() => {if(window.confirm(`Sei sicuro di voler eliminare la squadra ${squadra.nome}?`)){
				deleteSquadra(squadra.id);
			}}} sx={{position:"absolute",top: 8, right:8, color:"#ef4444",'&:hover': { backgroundColor: "#e1f5fe" }}}>
				❌
			</IconButton>
              <CardContent>
                <Typography variant="h6" sx={{ fontWeight: "bold" }}>🛡{squadra.nome}</Typography>
                <Typography variant="body2" color="text.secondary" sx={{ mt: 1 }}>
                  📍 Città: {squadra.citta}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  📆 Fondazione: {squadra.annoFondazione}
                </Typography>
                
                
                {squadra.giocatori && squadra.giocatori.length > 0 && (
                  <Box sx={{ mt: 2, display: "flex", flexWrap: "wrap", gap: 0.5 }}>
                    <Chip 
                      label={`Rosa: ${squadra.giocatori.length} giocatori`} 
                      size="small" 
                      color="primary"
                      variant="outlined" 
                    />
                  </Box>
                )}
              </CardContent>
            </Card>
          </Grid>
        ))}
      </Grid>
    </Box>
  );
}