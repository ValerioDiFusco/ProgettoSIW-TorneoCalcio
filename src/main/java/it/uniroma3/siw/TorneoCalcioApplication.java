package it.uniroma3.siw;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import it.uniroma3.siw.model.Partita;
import it.uniroma3.siw.model.Torneo;
import it.uniroma3.siw.repository.PartitaRepository;
import it.uniroma3.siw.repository.TorneoRepository;


@SpringBootApplication
public class TorneoCalcioApplication implements CommandLineRunner {
	
	private PartitaRepository partitaRepository;
	
	public TorneoCalcioApplication(PartitaRepository partitaRepository) {
		this.partitaRepository = partitaRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(TorneoCalcioApplication.class, args);
	}
	
	@Override
	@Transactional
	public void run(String...args) throws Exception{
		es();
	}
	
	private void es() {
		StopWatch watch = new StopWatch();
		watch.start("Prova");
		
		Partita partita = this.partitaRepository.findById(1L).get();
		partita.getSquadraAway().getNome();


		
		watch.stop();
		System.out.println(watch.prettyPrint());
	}
	

}
