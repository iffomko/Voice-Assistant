package com.iffomko.voiceAssistant.answer;

import com.iffomko.voiceAssistant.speech.YandexClient;
import com.iffomko.voiceAssistant.speech.data.RecognitionResponse;
import com.iffomko.voiceAssistant.speech.types.YandexVoice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Base64;

/**
 * <p>Класс бизнес-логики формирования ответа на вопрос</p>
 */
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

    /**
     * <p>Переводит текст в звук</p>
     * @param text - текст, который нужно озвучить
     * @param format - формат выходного звука (mp3, oggopus, lpcm)
     * @return - звук, закодированный в Base64
     */
    public String textToVoice(String text, String format) {
        if (format != null) {
            yandexClient.getSynthesis().setFormat(format);
        }

        yandexClient.getSynthesis().setVoice(YandexVoice.FILIPP);

        byte[] voice = yandexClient.synthesis(text);

        if (voice == null) {
            return null;
        }

        return Base64.getEncoder().encodeToString(voice);
    }
}
