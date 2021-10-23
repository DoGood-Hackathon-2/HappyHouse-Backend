package com.example.happyhousebackend.domain.daily.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DailyRequestDto {

    private String content;

    private String imageUrl;

}
