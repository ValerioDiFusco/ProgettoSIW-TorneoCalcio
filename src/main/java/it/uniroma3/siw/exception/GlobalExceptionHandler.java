package it.uniroma3.siw.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(TorneoNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleTorneoNotFound(TorneoNotFoundException e,Model model) {
		model.addAttribute("errorMessage", e.getMessage());
		return "error/404";
	}
	
	@ExceptionHandler(SquadraNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleSquadraNotFound(SquadraNotFoundException e, Model model) {
		model.addAttribute("errorMessage", e.getMessage());
		return "error/404";
	}
	
	@ExceptionHandler(GiocatoreNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleGiocatoreNotFound(GiocatoreNotFoundException e, Model model) {
		model.addAttribute("errorMessage", e.getMessage());
		return "error/404";
	}
	
	@ExceptionHandler(PartitaNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handlePartitaNotFound(PartitaNotFoundException e, Model model) {
		model.addAttribute("errorMessage", e.getMessage());
		return "error/404";
	}
	
	@ExceptionHandler(ArbitroNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleArbitroNotFound(ArbitroNotFoundException e, Model model) {
		model.addAttribute("errorMessage", e.getMessage());
		return "error/404";
	}
	@ExceptionHandler(AccessoVietatoException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public String handleAccessoVietato(AccessoVietatoException e, Model model) {
		model.addAttribute("errorMessage", "Non hai i permessi necessari per accedere a questa pagina");
		return "error/403";
	}
}
