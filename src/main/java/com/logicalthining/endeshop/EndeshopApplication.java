package com.logicalthining.endeshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 恩德生态项目
 * 启动时间 2019-10-29
 *
 * @since 下午 7:44 2019/10/29 0029
 **/
@ServletComponentScan
@EnableSwagger2
@MapperScan(value = "com.logicalthining.endeshop.dao")
@EnableTransactionManagement
@EnableConfigurationProperties
@SpringBootApplication
public class EndeshopApplication {

    public static void main(String[] args) {
        SpringApplication.run(EndeshopApplication.class, args);
    }

}
