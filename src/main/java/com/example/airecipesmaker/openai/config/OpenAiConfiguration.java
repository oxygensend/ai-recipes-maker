package com.example.airecipesmaker.openai.config;

import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class OpenAiConfiguration {
    @Value("${open.ai.secret-key}")
    private String secretKey;

    @Bean
    public OpenAiService openAiService() {
        return new OpenAiService(secretKey, Duration.ofSeconds(30));
    }

}
