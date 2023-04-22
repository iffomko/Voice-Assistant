package com.iffomko.voiceAssistant.answer;

import lombok.Data;

@Data
public class AnswerBody {
    private Integer userId;
    private String audio;
    private String format;
    private Boolean profanityFilter;
    private String encode;
}
