package br.com.casadocodigo.loja.controller;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.models.CarrinhoCompras;
import br.com.casadocodigo.loja.models.DadosPagamento;
import br.com.casadocodigo.loja.models.Usuario;

@RequestMapping("/pagamento")
@Controller
public class PagamentoController {
	
	
	@Autowired
	CarrinhoCompras carrinho;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private MailSender sender;
	
	@RequestMapping(value="/finalizar",method=RequestMethod.POST)
	public Callable<ModelAndView> finalizar(@AuthenticationPrincipal Usuario usuario, RedirectAttributes model) {// requisição assincrona
		
		return ()->{
			
			String uri = "http://book-payment.herokuapp.com/payment";	
			
			
			try {
				
				String response = restTemplate.postForObject(uri, new DadosPagamento(carrinho.getTotal()), String.class);
				
				
				System.out.println(response);
				
				
				//Enviar email
				enviaEmailCompraProduto(usuario);
				
				model.addFlashAttribute("sucesso",response);
				
				return new ModelAndView("redirect:/produtos");
				
				
			} catch (HttpClientErrorException e) {			
				e.printStackTrace();
				model.addFlashAttribute("falha","Valor maior que o permitido");
				return new ModelAndView("redirect:/produtos");
			}
			
		};
		
		
		
		
	}

	private void enviaEmailCompraProduto(Usuario usuario) {
		
		
		
		SimpleMailMessage email = new SimpleMailMessage();
		
		//Assunto
		email.setSubject("Compra finalizada com sucesso");
		
		//Para
		//email.setTo(usuario.getEmail());
		email.setTo("eduardo27_minotauro@hotmail.com");
				
		//Texto
		email.setText("Compra aprovada com sucesso no valor de " + carrinho.getTotal());
		
		//Quem esta enviando o emil
		//email.setFrom("eduardo.r.delfino@gmail.com");
		
		email.setFrom("eduardo.delfino@zup.com.br");
		
		//Enviar
		sender.send(email);
		
		
	}
	
	

}
