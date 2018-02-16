package br.com.casadocodigo.loja.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

//Controller que observa todos os outros controllers
@ControllerAdvice
public class ExceptionHandlerController {

	@ExceptionHandler(Exception.class)
	public ModelAndView trataExceptionGenerica(Exception exception) {
		System.out.println("Erro genérico acontecendo");
		exception.printStackTrace();//mostra no servidor
		
		ModelAndView modelAndView = new ModelAndView("error");// mostra pagina de error
		modelAndView.addObject("exception", exception);
		return modelAndView;
	}
	
}
