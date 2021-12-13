package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * <p>
 * swagger文档配置
 * </p>
 *
 * @author MrWen
 **/
@Configuration
//@EnableSwagger2 //旧版本
@EnableSwagger2WebMvc//新版本
public class SwaggerConfig {

    @Bean
    public Docket buildDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(buildApiInf())
                .select()
                //controller包路径
                .apis(RequestHandlerSelectors.basePackage("com.example.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 描述信息
     */
    private ApiInfo buildApiInf() {
        return new ApiInfoBuilder()
                .title("swagger测试")
                .version("1.0")
                .description("swagger测试描述")
                .termsOfServiceUrl("http://www.example.com")
                .build();
    }

}
