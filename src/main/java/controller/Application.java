package controller;



import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

@Configuration
@SpringBootApplication


@PropertySource("classpath:application.properties")
public class Application extends SpringBootServletInitializer {
		@Bean  
	    public ResourceBundleMessageSource messageSource() {  
	        ResourceBundleMessageSource source = new ResourceBundleMessageSource();  
	        source.setBasename("i18n/messages");  
	        source.setUseCodeAsDefaultMessage(true);  
	        return source;  
	}
	@Override
	protected SpringApplicationBuilder configure(final SpringApplicationBuilder application){
		return application.sources(Application.class);
		
	}
	 @Bean
	    public RequestMappingHandlerAdapter  annotationMethodHandlerAdapter()
	    {
	        final RequestMappingHandlerAdapter annotationMethodHandlerAdapter = new RequestMappingHandlerAdapter();
	        final MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter = new MappingJackson2HttpMessageConverter();

	        List<HttpMessageConverter<?>> httpMessageConverter = new ArrayList<HttpMessageConverter<?>>();
	        httpMessageConverter.add(mappingJacksonHttpMessageConverter);

	        String[] supportedHttpMethods = { "POST", "GET", "PUT","DELETE" };

	        annotationMethodHandlerAdapter.setMessageConverters(httpMessageConverter);
	        annotationMethodHandlerAdapter.setSupportedMethods(supportedHttpMethods);

	        return annotationMethodHandlerAdapter;
	    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
	}
