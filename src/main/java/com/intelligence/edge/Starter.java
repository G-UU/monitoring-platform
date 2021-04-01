package com.intelligence.edge;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @author Samoyed
 * @date 2019/07/20
 **/
@SpringBootApplication
@EnableScheduling
public class Starter {
    public static void main(String[] args) {

        SpringApplication.run(Starter.class, args);
    }

    /**
     * 跨域配置
     */
    /*@Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        //允许任何域名、任何请求头、任何方式访问提交方法
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        //对所有接口有效
        source.registerCorsConfiguration("/*",config);
        return new CorsFilter(source);
    }*/

}
