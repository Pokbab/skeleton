package com.cdg.skeleton.config;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.paths.RelativeSwaggerPathProvider;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;

/**
 * @author Kanghoon Choi
 */
@Configuration
@PropertySource("classpath:properties/swagger.properties")
@ComponentScan(basePackages = {"com.cdg.skeleton"})
@EnableWebMvc
@EnableSwagger
public class SwaggerConfig implements ServletContextAware {
	
	@Autowired
	private Environment environment;
	
	private SpringSwaggerConfig springSwaggerConfig;
	
	private ServletContext servletContext;
	
	@Autowired
	public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
		this.springSwaggerConfig = springSwaggerConfig;
	}
	
	@Bean
	public SwaggerSpringMvcPlugin customImplementation() {
		RelativeSwaggerPathProvider relativeSwaggerPathProvider = new RelativeSwaggerPathProvider(servletContext);
		relativeSwaggerPathProvider.setApiResourcePrefix(environment.getProperty("swagger.resource_prefix"));
		return new SwaggerSpringMvcPlugin(this.springSwaggerConfig).pathProvider(relativeSwaggerPathProvider);
	}
	
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
}