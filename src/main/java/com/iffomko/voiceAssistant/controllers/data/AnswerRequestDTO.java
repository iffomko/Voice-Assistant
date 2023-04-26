package com.iffomko.voiceAssistant.controllers.data;

import lombok.Data;

@Data
public class AnswerRequestDTO {
    private Integer userId;
    private String audio;
    private String format;
    private Boolean profanityFilter;
    private String encode;
}
