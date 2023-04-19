package com.iffomko.voiceAssistant.answer;

import com.iffomko.voiceAssistant.speech.YandexClient;
import com.iffomko.voiceAssistant.speech.responses.RecognitionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
@Component
@Slf4j
public class AnswerService {
    @Autowired
    private YandexClient yandexClient;

    /**
     * <p>В параметрах передается звук в формате Base64 и на выходе возвращается текстовое содержание этого звука</p>
     * @param voice - звук в кодировке base64
     * @return - возвращается текст звука или <code>null</code>, если звук не закодирован в Base64
     */
    public String audioToText(String voice, String format, Boolean profanityFilter) {
        byte[] bytes = null;

        try {
            bytes = Base64.getDecoder().decode(voice);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return null;
        }

        if (format != null) {
            yandexClient.getRecognition().setFormat(format);
        }

        if (profanityFilter != null) {
            yandexClient.getRecognition().setProfanityFilter(profanityFilter);
        }

        RecognitionResponse response = yandexClient.recognise(bytes);

        if (response == null) {
            return null;
        }

        return yandexClient.recognise(bytes).getResult();
    }

    public String textToVoice(String text) {
        return null;
    }
}
