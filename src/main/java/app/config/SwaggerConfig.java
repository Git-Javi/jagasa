package app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket personasApi() {

		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("app.controller"))
				.paths(PathSelectors.regex("/persona.*"))
				.build();
	}

	private ApiInfo apiInfo() {

		return new ApiInfoBuilder()
				.title("API REST de Personas")
				.version("1.0")
				.license("(Licencia)")
				.description("API RESTful de personas con BDD en memoria")
				.build();
	}
}
