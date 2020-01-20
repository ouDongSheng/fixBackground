package com.logicalthining.endeshop.conf;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

/**
 * @author chenlijia
 * @version 1.0
 * @since 2019/8/5 0005 上午 11:31
 **/

    /**
     * @author ShiXian
     * @version 1.0
     * @Description swagger配置
     * @date 2018-7-21
     */
    @Configuration
    @EnableWebMvc
    @EnableSwagger2
    @ComponentScan(basePackages = "com.logicalthining")
    public class SwaggerConf extends WebMvcConfigurationSupport {

        @Bean
        public Docket appApi() {
            return new Docket(DocumentationType.SWAGGER_2)
                    .groupName("ende")
                    .apiInfo(apiInfo("APP-接口"))
                    .produces(Sets.newHashSet("application/json;charset=UTF-8"))
                    .pathMapping("/")
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("com.logicalthining.endeshop"))
                    .paths(PathSelectors.any()).build()
                    .globalOperationParameters(parameter());
        }
//        @Bean
//        public Docket manageApi() {
//            return new Docket(DocumentationType.SWAGGER_2)
//                    .groupName("shop-manager")
//                    .apiInfo(apiInfo("后台-接口"))
//                    .produces(Sets.newHashSet("application/json;charset=UTF-8"))
//                    .pathMapping("/")
//                    .select()
//                    .apis(RequestHandlerSelectors.basePackage("com.logicalthinking.controller"))
//                    .paths(PathSelectors.any()).build();
//        }



        private ApiInfo apiInfo(String desc) {
            return new ApiInfoBuilder()
                    .title("集需购项目接口文档")
                    .description(SwaggerInfo.init(desc))
                    .version("1.0.0")
                    .termsOfServiceUrl("NO terms of service")
                    .contact(new Contact("xxx", "", ""))
                    .build();
        }

        /**
         * 添加head参数
         *
         * @return List<Parameter>
         */
        private List<Parameter> parameter() {
            ParameterBuilder tokenPar = new ParameterBuilder();
            List<Parameter> pars = Lists.newArrayList();
            tokenPar.name("x-shop-token").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
            pars.add(tokenPar.build());
            return pars;
        }
    }
