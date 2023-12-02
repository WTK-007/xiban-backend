package com.wtk.xiban.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;


@Configuration
@EnableSwagger2WebMvc
@Profile({"dev","test"})
public class SwaggerConfig {

    @Bean(value = "defaultApi2")
    public Docket defaultApi2(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 这里一定要标记控制器的位置
                .apis(RequestHandlerSelectors.basePackage("com.wtk.xiban.controller"))
                .paths(PathSelectors.any())
                .build();
    }


    /**
     * api 信息
     * @return
     */

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("曦伴伙伴匹配系统")
                .description("曦伴伙伴匹配系统接口文档")
                .termsOfServiceUrl("https://github.com/wtk-007")
                .contact(new Contact("xiyan","https://github.com/wtk-007","2570879712@qq.com"))
                .version("1.0")
                .build();
    }

}

