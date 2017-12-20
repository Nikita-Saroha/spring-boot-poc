package com.profiling.consul.controller.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
        		.select()
        		.apis(RequestHandlerSelectors.any())
        		.paths(PathSelectors.regex("/v1.*"))
        		.build()
        		.useDefaultResponseMessages(false)
        		.apiInfo(apiInfo());
    }
	
	private ApiInfo apiInfo() {
	    ApiInfo apiInfo = new ApiInfo(
	      "Api Documentation",
	      "Api Documentation",
	      null,
	      null,
	      null,
	      null,
	      null,
	      new ArrayList<>());
	    return apiInfo;
	}

}
