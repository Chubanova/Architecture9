package com.chubanova;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
//https://github.com/babikovpavel/starship-battle/blob/939b714a1b23b1a82125a963f7178b5966601680/hw9/src/main/java/ru/otus/homework9/Homework9Application.java