package com.iffomko.voiceAssistant.openAI;

import com.iffomko.voiceAssistant.openAI.data.ModelBodyDTO;
import com.iffomko.voiceAssistant.openAI.data.ModelResponse;
import com.iffomko.voiceAssistant.openAI.data.OpenAIMessage;
import com.iffomko.voiceAssistant.openAI.types.OpenAIModels;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * <p>
 *      Класс-репозиторий, который умеет подключаться к OpenAI API и задавать вопросы модели
 * </p>
 */
@Slf4j
public class AIModel {
    private OpenAIModels model;
    private final String apiKey;
    private final static String URL = "https://api.openai.com/v1/chat/completions";

    /**
     * <p>Создает объект этого класса, который делает запросы к модели</p>
     * @param apiKey ключ, с помощью которого можно сделать запрос
     */
    public AIModel(String apiKey) {
        this.apiKey = apiKey;
        this.model = OpenAIModels.CHAT_GPT_3_5;
    }

    /**
     * <p>Делает запрос к OpenAI API к той модели, которая установлена в соответствующем поле</p>
     * @param messages предыдущие сообщения в переписке
     * @param mess текущий вопрос к модели
     * @return возвращает ответ модели типа <code>ModelResponse</code>
     */
    protected ModelResponse ask(List<OpenAIMessage> messages, String mess) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<ModelBodyDTO> request = new HttpEntity<>(
                new ModelBodyDTO(model.getModel(), mess, model.getTemperature(), messages),
                headers
        );

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<ModelResponse> response = null;

        try {
            response = restTemplate.exchange(
                    URL, HttpMethod.POST, request, ModelResponse.class
            );
        } catch (RestClientException e) {
            log.error(e.getMessage());
        }

        if (response == null || response.getBody() == null) {
            log.error("Failed to make a request to ChatGPT");
            return null;
        }

        log.info("The request to ChatGPT was successful");

        return response.getBody();
    }

    public OpenAIModels getModel() {
        return model;
    }

    public void setModel(@NonNull OpenAIModels model) {
        this.model = model;
    }
}
