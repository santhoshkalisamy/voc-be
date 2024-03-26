package dev.santhoshkalisamy.voiceofconsumer.exception;

import dev.santhoshkalisamy.voiceofconsumer.entity.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePostNotFoundException(
            PostNotFoundException postNotFoundException) {
        ErrorResponse errorResponse = new ErrorResponse(101, "Sorry, we cannot find a post for the requested id");
        return ResponseEntity.status(404).body(errorResponse);
    }

}
