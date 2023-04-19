package com.iffomko.voiceAssistant.speech;

import com.iffomko.voiceAssistant.speech.responses.RecognitionResponse;

public class YandexClient {
    private final YandexRecognition recognition;

    public YandexClient(String apiKey) {
        this.recognition = new YandexRecognition(apiKey);
    }

    public YandexRecognition getRecognition() {
        return recognition;
    }

    public RecognitionResponse recognise(byte[] voice) {
        return recognition.recognition(voice);
    }
}
