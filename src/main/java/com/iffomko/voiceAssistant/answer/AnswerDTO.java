package com.iffomko.voiceAssistant.answer;

import lombok.Data;

@Data
public class AnswerDTO {
    private String voice = null;
    private String encode = null;
    private String format = null;
    private AnswerError error;

    public AnswerDTO() {}

    public AnswerDTO(String byteAudio, String encode, String format) {
        this.voice = byteAudio;
        this.encode = encode;
        this.format = format;
    }

    public AnswerDTO(AnswerError error) {
        this.error = error;
    }
}
