package com.projectx.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
    // 임의로 외부에서 값을 받아올 RestTemplate 값을 주입 받기 위한 Bean 등록

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
