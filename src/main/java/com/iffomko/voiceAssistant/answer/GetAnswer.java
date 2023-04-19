package com.iffomko.voiceAssistant.answer;

import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/answer")
public class GetAnswer {
    @Resource(name = "AnswerService")
    private AnswerService answerService;

    @GetMapping(produces="application/json")
    @ResponseBody()
    public ResponseEntity<AnswerDTO> getAnswer(
            @RequestParam String audio,
            @RequestParam(required = false) String format,
            @RequestParam(required = false) Boolean profanityFilter
    ) {
        audio = audio.replace(" ", "+");

        String text = answerService.audioToText(audio, format, profanityFilter);

        return ResponseEntity.ok(new AnswerDTO(text, "base64"));
    }
}
