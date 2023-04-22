package com.iffomko.voiceAssistant.openAI.data;

import lombok.Data;

import java.util.List;

/**
 * <p>Объект, который конвертируется в JSON для передачи в теле запроса к OpenAI API</p>
 */
@Data
public class ModelBodyDTO {
    private String model;
    private String prompt;
    private Double temperature;
    private List<OpenAIMessage> messages;

    public ModelBodyDTO() {}

    public ModelBodyDTO(String model, String prompt, Double temperature, List<OpenAIMessage> messages) {
        this.model = model;
        this.prompt = prompt;
        this.temperature = temperature;
        this.messages = messages;
    }
}
