package com.iffomko.voiceAssistant.configurations;

import com.iffomko.voiceAssistant.answer.AnswerService;
import com.iffomko.voiceAssistant.speech.YandexClient;
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
}
