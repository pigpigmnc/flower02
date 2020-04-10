package com.zsc.flower;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@SpringBootApplication
@MapperScan("com.zsc.flower.dao")
@EnableScheduling
public class FlowerApplication {


    @Bean
    public RestTemplate restTemplate() {
        //防止超时
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        //建立连接所用的时间 5s
        simpleClientHttpRequestFactory.setConnectTimeout(5000);
        //服务器读取可用资源的时间 10s
        simpleClientHttpRequestFactory.setReadTimeout(600000);
        return new RestTemplate(simpleClientHttpRequestFactory);
    }

    public static void main(String[] args) {
        SpringApplication.run(FlowerApplication.class, args);
    }

}
