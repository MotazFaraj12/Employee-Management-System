package com.example.EmployeeManagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class configuration {
   @Bean
   public RestTemplate restTemplate(){
       return new RestTemplate();
   }
   @Bean
   public WebClient webClient(WebClient.Builder webClientBuilder) {
       return webClientBuilder.baseUrl("http://localhost:8081").build();
   }
}
