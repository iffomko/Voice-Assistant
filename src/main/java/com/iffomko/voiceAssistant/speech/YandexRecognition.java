package com.iffomko.voiceAssistant.speech;

import com.iffomko.voiceAssistant.speech.responses.RecognitionResponse;
import com.iffomko.voiceAssistant.speech.types.YandexFormat;
import com.iffomko.voiceAssistant.speech.types.YandexLanguage;
import com.iffomko.voiceAssistant.speech.types.YandexTopic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Slf4j
public class YandexRecognition {
    private final String apiKey;
    private YandexLanguage lang = null;
    private YandexTopic topic = null;
    private boolean profanityFilter = false;
    private String format = "oggopus";
    public final static String URL = "https://stt.api.cloud.yandex.net/speech/v1/stt:recognize";

    public YandexRecognition(String apiKey) {
        this.apiKey = apiKey;
    }

    private String getQueryParams() {
        StringBuilder queries = new StringBuilder();

        if (lang != null) {
            queries.append("lang=");
            queries.append(lang.getLang());
        }

        if (topic != null) {
            if (!queries.isEmpty()) {
                queries.append("&");
            }

            queries.append("topic=");
            queries.append(lang.getLang());
        }

        if (!queries.isEmpty()) {
            queries.append("&");
        }

        queries.append("profanityFilter=");
        queries.append(profanityFilter);

        if (!queries.isEmpty()) {
            queries.append("&");
        }

        queries.append("format=");
        queries.append(format);

        return queries.toString();
    }

    protected RecognitionResponse recognition(byte[] voice) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "Api-Key " + apiKey);

        HttpEntity<byte[]> request = new HttpEntity<>(voice, headers);

        ResponseEntity<RecognitionResponse> response = null;

        try {
            response =  restTemplate.exchange(
                    URL + "?" + getQueryParams(),
                    HttpMethod.POST,
                    request,
                    RecognitionResponse.class
            );
        } catch (RestClientException e) {
            log.error(e.getMessage());
        }

        if (response == null || response.getBody() == null) {
            return null;
        }

        log.info(
                "The Yandex API text recognition request was executed successfully with the return response: " +
                        response.getBody().getResult()
        );

        return response.getBody();
    }

    public YandexLanguage getLang() {
        return lang;
    }

    public void setLang(YandexLanguage lang) {
        this.lang = lang;
    }

    public YandexTopic getTopic() {
        return topic;
    }

    public void setTopic(YandexTopic topic) {
        this.topic = topic;
    }

    public boolean isProfanityFilter() {
        return profanityFilter;
    }

    public void setProfanityFilter(boolean profanityFilter) {
        this.profanityFilter = profanityFilter;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
