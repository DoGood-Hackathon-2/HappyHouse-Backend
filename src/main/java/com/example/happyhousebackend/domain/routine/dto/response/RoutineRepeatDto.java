package com.example.happyhousebackend.domain.routine.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter @Setter
@Builder
public class RoutineRepeatDto {

    private String title;

    private String subTitle;

    private String requesterImage;

    private LocalDate date;

    private List<Integer> dayList;

    private LocalTime time;

    private List<String> memberList;

}
