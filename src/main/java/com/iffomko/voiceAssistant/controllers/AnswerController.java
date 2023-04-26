package com.iffomko.voiceAssistant.controllers;

import com.iffomko.voiceAssistant.APIs.speech.types.YandexFormat;
import com.iffomko.voiceAssistant.controllers.errors.AnswerError;
import com.iffomko.voiceAssistant.controllers.data.AnswerRequestDTO;
import com.iffomko.voiceAssistant.controllers.data.AnswerResponseDTO;
import com.iffomko.voiceAssistant.controllers.services.AnswerService;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/answer")
public class AnswerController {
    @Resource(name = "AnswerService")
    private AnswerService answerService;

    /**
     * <p>Контроллер, который отвечает за ответ на вопрос, который содержится в аудио</p>
     */
    @PostMapping(produces="application/json")
    @ResponseBody()
    public ResponseEntity<AnswerResponseDTO> getAnswer(@RequestBody AnswerRequestDTO body) {
        if (
                body.getFormat() != null &&
                !body.getFormat().equals(YandexFormat.OGGOPUS.getFormat()) &&
                !body.getFormat().equals(YandexFormat.MP3.getFormat()) &&
                !body.getFormat().equals(YandexFormat.LPCM.getFormat())
        ) {
            AnswerError error = new AnswerError();
            error.setCode(HttpStatus.BAD_REQUEST.value());
            error.setMessage("Вы ввели некорректный формат аудио: " + body.getFormat());

            return ResponseEntity.badRequest().body(new AnswerResponseDTO(error));
        }

        if (body.getFormat() == null) {
            body.setFormat(YandexFormat.OGGOPUS.getFormat());
        }

        if (body.getProfanityFilter() == null) {
            body.setProfanityFilter(false);
        }

        String response = answerService.getAnswer(
                body.getUserId(),
                body.getAudio(),
                body.getFormat(),
                body.getProfanityFilter()
        );

        if (response == null) {
            AnswerError error = new AnswerError();
            error.setCode(HttpStatus.BAD_REQUEST.value());
            error.setMessage("Не удалось получить ответ");

            return ResponseEntity.badRequest().body(new AnswerResponseDTO(error));
        }

        return ResponseEntity.ok(new AnswerResponseDTO(response, "base64", body.getFormat()));
    }
}