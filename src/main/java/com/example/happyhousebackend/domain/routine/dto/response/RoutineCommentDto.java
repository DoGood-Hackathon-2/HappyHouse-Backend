package com.example.happyhousebackend.domain.routine.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@Getter @Setter
@Builder
public class RoutineCommentDto {

    private String nickname;

    private String memberImage;

    private String routineImage;

    private String comment;

    private LocalDateTime date;

}
