package com.iffomko.voiceAssistant.answer;

import com.iffomko.voiceAssistant.speech.types.YandexFormat;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/answer")
public class GetAnswer {
    @Resource(name = "AnswerService")
    private AnswerService answerService;

    /**
     * <p>Контроллер, который отвечает за ответ на вопрос, который содержится в аудио</p>
     */
    @GetMapping(produces="application/json")
    @ResponseBody()
    public ResponseEntity<AnswerDTO> getAnswer(
            @RequestParam String audio,
            @RequestParam(required = false) String format,
            @RequestParam(required = false) Boolean profanityFilter
    ) {
        if (
                format != null &&
                !format.equals(YandexFormat.OGGOPUS.getFormat()) &&
                !format.equals(YandexFormat.MP3.getFormat()) &&
                !format.equals(YandexFormat.LPCM.getFormat())
        ) {
            AnswerError error = new AnswerError();
            error.setCode(HttpStatus.BAD_REQUEST.value());
            error.setMessage("Вы ввели некорректный формат аудио: " + format);

            return ResponseEntity.badRequest().body(new AnswerDTO(error));
        }

        audio = audio.replace(" ", "+");

        String text = answerService.audioToText(audio, format, profanityFilter);
        String response = answerService.textToVoice(text, format);

        return ResponseEntity.ok(new AnswerDTO(response, "base64", format));
    }
}
