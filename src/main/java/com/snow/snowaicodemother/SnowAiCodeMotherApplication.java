package com.snow.snowaicodemother;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.snow.snowaicodemother.mapper")
public class SnowAiCodeMotherApplication {

    public static void main(String[] args) {
        SpringApplication.run(SnowAiCodeMotherApplication.class, args);
    }

}
