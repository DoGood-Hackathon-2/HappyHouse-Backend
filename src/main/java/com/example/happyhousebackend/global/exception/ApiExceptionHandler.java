package com.example.happyhousebackend.global.exception;

import com.example.happyhousebackend.global.message.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ResponseMessage> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("IllegalArgumentException " + e.getMessage());
        return ResponseEntity.badRequest().body(ResponseMessage.of("IllegalArgumentException"));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ResponseMessage> exception(Exception e) {
        log.error("Exception " + e.getMessage());
        return ResponseEntity.internalServerError().body(ResponseMessage.of("InternalServerError"));
    }

}