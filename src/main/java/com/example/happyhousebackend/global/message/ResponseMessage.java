package com.example.happyhousebackend.global.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ResponseMessage {

    private String message;
    private Object data;

    public static ResponseMessage of(String message) {
        return of(message, null);
    }

    public static ResponseMessage of(String message, Object data) {
        return ResponseMessage.builder()
                .message(message)
                .data(data)
                .build();
    }
}
