package com.payment.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
class SwaggerConfig {

    private static final String REST_PAI = "api";
    
    @Bean
    public Docket api() {
        ApiInfo restAPIInfo = buildApiInfo(REST_PAI);

        ParameterBuilder aParameterBuilder = new ParameterBuilder();
        aParameterBuilder.name("Authorization").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        List<Parameter> aParameters = new ArrayList<>();
        aParameters.add(aParameterBuilder.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.payment.controllers"))
                .paths(regex(".*/" + REST_PAI + ".*"))
                .build()
                .apiInfo(restAPIInfo).globalOperationParameters(aParameters);
    }

    private List<ResponseMessage> buildGlobalResponses() {
        return newArrayList(new ResponseMessageBuilder()
                .code(500)
                .message("Unexpected error during execution")
                .responseModel(new ModelRef("Error"))
                .build(),
        		new ResponseMessageBuilder() 
        	      .code(403)
        	      .message("Forbidden!")
        	      .build());
    }

    private ApiInfo buildApiInfo(String version) {
        return new ApiInfoBuilder()
                .title("Fresher API Documentation")
                .description("Fresher API")
                .version(version)
                .build();
    }
}