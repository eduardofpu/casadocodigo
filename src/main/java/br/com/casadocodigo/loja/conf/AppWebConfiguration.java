package br.com.casadocodigo.loja.conf;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.hibernate.validator.util.privilegedactions.GetMethod;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.google.common.cache.CacheBuilder;

import br.com.casadocodigo.loja.controller.HomeController;
import br.com.casadocodigo.loja.daos.ProdutoDao;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.models.CarrinhoCompras;

@EnableWebMvc
@ComponentScan(basePackageClasses = { HomeController.class, ProdutoDao.class, FileSaver.class, CarrinhoCompras.class })
@EnableCaching // Para dar performace guardando cache
public class AppWebConfiguration extends WebMvcConfigurerAdapter {

	@Bean
	public InternalResourceViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");

		// Todos o Bens ficam disponiveis como atributo na nossa jsp
		// resolver.setExposeContextBeansAsAttributes(true);

		resolver.setExposedContextBeanNames("carrinhoCompras");

		return resolver;

	}

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

		messageSource.setBasename("/WEB-INF/messages");
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(1);

		return messageSource;
	}

	@Bean
	public FormattingConversionService mvcConversionService() {
		DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();

		DateFormatterRegistrar registrar = new DateFormatterRegistrar();

		registrar.setFormatter(new DateFormatter("dd/MM/yyyy"));
		registrar.registerFormatters(conversionService);

		return conversionService;

	}

	@Bean
	public MultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}

	@Bean
	public RestTemplate restTemplate() {

		return new RestTemplate();
	}

	@Bean
	public CacheManager cacheManager() {

		// return new ConcurrentMapCacheManager(); // somente para desenvolvimento

		// tamanho maximo do cache o guava para utilizar em produção
		CacheBuilder<Object, Object> builder = CacheBuilder.newBuilder().maximumSize(100).expireAfterAccess(5,
				TimeUnit.MINUTES); // Poderia ser dias ex: .expireAfterAccess(5, TimeUnit.DAYS);
		GuavaCacheManager manager = new GuavaCacheManager();
		manager.setCacheBuilder(builder);
		return manager;

	}

	@Bean
	public ViewResolver contentNegotiationViewResolver(ContentNegotiationManager manager) {
		List<ViewResolver> viewResolvers = new ArrayList<>();
		viewResolvers.add(internalResourceViewResolver());
		viewResolvers.add(new JsonViewResolver());

		ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
		resolver.setViewResolvers(viewResolvers);
		resolver.setContentNegotiationManager(manager);

		return resolver;
	}

	@Override // fala para o servlet do spring como sera capiturado as requisições defout com
				// css, js
	// precisa extender para AppWebConfiguration extends WebMvcConfigurerAdapter
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();// tem que estar abilidaddo passando para o tomcat configurer.enable().
	}

	@Override // intercepitar locale
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LocaleChangeInterceptor());
	}

	@Bean // como o spring vai instanciar o locale
	public LocaleResolver localeResolver() {
		return new CookieLocaleResolver();
	}

	// entrar no seu gmail procurar configurações : encaminhamento e POP/IMAP :
	// ativar IMAP: ABRIRÁ UMA PAGINA PEDINDO PARA VOCÊ ATIVAR: PERMITIR APLICATIVOS
	// MENOS SEGUROS: ATIVAR
	@Bean
	public MailSender mailSender() {

		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		
		//gmail(mailSender);		  
	    emailSender(mailSender);	
		//emailTrap(mailSender);
		return mailSender;

	}

	
	public void gmail(JavaMailSenderImpl mailSender) {

		// Envio para o gmail e dev
		// JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setUsername("nome.exemplo@gmail.com");
		mailSender.setPassword("senha");
		mailSender.setPort(587);
		Properties mailProperties = new Properties();
		mailProperties.put("mail.smtp.auth", true);
		mailProperties.put("mail.smtp.starttls.enable", true);
		mailSender.setJavaMailProperties(mailProperties);

	}

	
	public void emailSender(JavaMailSenderImpl mailSender) {

		// Plano simples heroku instalar o sendGrid no prompt verifique os dados
		// instalado : heroku config
		// JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.sendgrid.net");
		mailSender.setUsername("app88243237@heroku.com");
		mailSender.setPassword("mrnnta0a4369");
		mailSender.setPort(25);
		Properties mailProperties = new Properties();
		mailProperties.put("mail.smtp.auth", true);
		mailProperties.put("mail.smtp.starttls.enable", true);
		mailSender.setJavaMailProperties(mailProperties);

	}
	
	public void emailTrap(JavaMailSenderImpl mailSender) {

		//Envio para o Heroku em produção no heroku instalar o mailtrap para dev		  
		  mailSender.setHost("smtp.mailtrap.io");
		  mailSender.setUsername("79fd3d0a7a93b3");
		  mailSender.setPassword("977bd0f890f140"); 
		  mailSender.setPort(2525);
		  Properties mailProperties = new Properties();
		  mailProperties.put("mail.smtp.auth", true);
		  mailProperties.put("mail.smtp.starttls.enable", true);
		  mailSender.setJavaMailProperties(mailProperties);

	}

}
