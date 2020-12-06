package io.labsit.interview.challenge.eduardo.web.config;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger Configuration Class.
 * 
 * @author Eduardo Fillipe da Silva Reis
 *
 */
@Configuration
public class SwaggerConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build().apiInfo(apiInfo()).host("");
	}
	
	ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Lab Sit Banking API").description(
				"Challenge proposed by the LabSit's recruit team during a Java developer job interview.")
				.license("Apache 2.0")
				.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
				.termsOfServiceUrl("")
				.version("1.0.0")
				.contact(new Contact("Eduardo Fillipe da Silva Reis", "https://www.linkedin.com/in/eduardo-fillipe-silva-reis/", "eduardo556@live.com"))
				.build();
	}
	
}
