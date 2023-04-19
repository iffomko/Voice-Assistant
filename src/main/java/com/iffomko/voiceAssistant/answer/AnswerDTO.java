package com.iffomko.voiceAssistant.answer;

public class AnswerDTO {
    private String byteAudio;

    private String encode;

    public AnswerDTO(String byteAudio, String encode) {
        this.byteAudio = byteAudio;
        this.encode = encode;
    }

    public String getByteAudio() {
        return byteAudio;
    }

    public String getEncode() {
        return encode;
    }

    public void setByteAudio(String byteAudio) {
        this.byteAudio = byteAudio;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }
}
