package br.com.casadocodigo.loja.conf;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ServletSpringMVC extends AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		return new Class[] {SecurityConfiguration.class,
				AppWebConfiguration.class,JPAConfiguration.class,JPAProductionConfiguration.class};
	}
	// mapea a classe AppWebConfiguraion
	@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO Auto-generated method stub
		return new Class[] {};
	}

	@Override
	protected String[] getServletMappings() {
		// TODO Auto-generated method stub
		return new String[] {"/"};
	}
	
	
	//Tratamento de filtros de caracteres
	@Override
	protected Filter[] getServletFilters() {
		
		CharacterEncodingFilter encodingFilter	= new CharacterEncodingFilter();
		encodingFilter.setEncoding("UTF-8");
		
		return new Filter[] {encodingFilter, new OpenEntityManagerInViewFilter()};
	}
	
	@Override
    protected void customizeRegistration(Dynamic registration) {
        registration.setMultipartConfig(new MultipartConfigElement(""));
    }
	
	//Para cololcar em produção e necessário comentar dev e vice e versa
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		
		//servletContext.setInitParameter("spring.profiles.active", "dev");
		String dev = "dev";	
		String prod = "prod";
		String profiles = "spring.profiles.active";
		
		if(dev.equals("dev")) {
			
			super.onStartup(servletContext);
			servletContext.addListener(RequestContextListener.class);
			servletContext.setInitParameter(profiles, dev);
			
		}else {
			
			super.onStartup(servletContext);
			servletContext.addListener(RequestContextListener.class);
			servletContext.setInitParameter(profiles, prod);
			
		}
				
				
	}
	
	

}
