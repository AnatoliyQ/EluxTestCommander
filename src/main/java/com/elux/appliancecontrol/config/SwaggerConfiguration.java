package com.elux.appliancecontrol.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Optional;

import static com.google.common.base.Predicates.not;
import static springfox.documentation.builders.PathSelectors.regex;


@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfiguration {


    private final BuildProperties buildProperties;

    public SwaggerConfiguration(@Autowired(required = false) BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(not(regex("/error")))
                .build()
                .enableUrlTemplating(true);
    }


    private ApiInfo apiInfo() {
        return Optional.ofNullable(buildProperties)
                .map(properties -> new ApiInfoBuilder()
                        .title(properties.getName())
                        .version(properties.getVersion())
                        .build())
                .orElse(new ApiInfoBuilder().build());
    }
}
