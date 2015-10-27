package test.restservices;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import controller.Application;


@EnableWebMvc
@Configuration
@Import(Application.class)
@ImportResource("classpath:mock-beans.xml")
public class IntegrationTestsApplicationConfiguration {

}

