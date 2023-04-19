package com.iffomko.voiceAssistant.speech.types;

public enum YandexFormat {
    MP3("mp3"),
    OGGOPUS("oggopus"),
    LPCM("lpcm");

    private final String format;

    YandexFormat(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }
}
