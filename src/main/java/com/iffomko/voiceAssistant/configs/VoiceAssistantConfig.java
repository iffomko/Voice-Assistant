package com.iffomko.voiceAssistant.configs;

import com.iffomko.voiceAssistant.controllers.services.AnswerService;
import com.iffomko.voiceAssistant.APIs.openAI.AIService;
import com.iffomko.voiceAssistant.APIs.speech.YandexClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan("com.iffomko.voiceAssistant")
public class VoiceAssistantConfig {
    @Bean("AnswerService")
    @Scope("prototype")
    public AnswerService getAnswerService() {
        return new AnswerService();
    }

    @Bean("yandexClient")
    public YandexClient getYandexRecognition() {
        String apiKey = System.getenv("API_KEY");

        return new YandexClient(apiKey);
    }

    @Bean("AIService")
    public AIService getAIService() {
        String apiKey = System.getenv("OPEN_AI_API_KEY");

        return new AIService(apiKey);
    }
}
