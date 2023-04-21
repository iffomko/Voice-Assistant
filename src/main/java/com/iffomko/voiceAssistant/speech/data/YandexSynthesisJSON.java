package com.iffomko.voiceAssistant.speech.data;

import lombok.Data;

/**
 * <p>
 *     Объект тела запроса синтеза текста. В нем содержатся 3 поля: название голоса синтеза, текст, который надо озвучить,
 *     и язык озвучки
 * </p>
 */
@Data
public class YandexSynthesisJSON {
    private String voice;
    private String text;
    private String lang;
}
