package com.shicheng.fusion.article.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Program: fusion
 * @Date: 2021/4/9 23:59
 * @Author: shicheng
 * @Description:
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                // 扫包的范围,也就是把哪些包生成api文档
                .apis(RequestHandlerSelectors.basePackage("com.shicheng.fusion.article.controller")).paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo() {
        //title文档标题
        //description文档描述
        //termsOfServiceUrl自定义地址
        //version版本号
        return new ApiInfoBuilder().title("超级博客").description("文章微服务模块")
                .termsOfServiceUrl("http://shicheng.xyz")
                // .contact(contact)
                .version("1.0").build();
    }

}
