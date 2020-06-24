package com.ajith.product.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger configuration
 * 
 * @author Ajith Kumar
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.ajith.product.controller")).paths(PathSelectors.any())
				.build().apiInfo(metaData()).useDefaultResponseMessages(false);
	}

	private ApiInfo metaData() {
		return new ApiInfoBuilder().title("Product API ").description("Product API").version("version 1.0.1")
				.contact(new Contact("", "", "")).build();
	}
}