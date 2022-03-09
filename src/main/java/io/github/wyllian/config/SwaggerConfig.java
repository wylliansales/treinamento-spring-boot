package io.github.wyllian.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
//@EnableSwagger2
public class SwaggerConfig {
    
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .useDefaultResponseMessages(false)
            .select()
            .apis(RequestHandlerSelectors.basePackage("io.github.wyllian.rest.controller"))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo());
            
    }
    
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("Treinamento API")
            .description("Implementação de swagger")
            .version("1.0")
            .contact(contact())
            .build();
    }
    
    private Contact contact() {
        return new Contact(
            "Wyllian Sales", 
            "https://github.com/wylliansales", 
            "wylliansalles@gmail.com"
        );
    }
}
