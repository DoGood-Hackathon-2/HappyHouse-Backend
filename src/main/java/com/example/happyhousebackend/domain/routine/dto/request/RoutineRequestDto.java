package com.example.happyhousebackend.domain.routine.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Builder
public class RoutineRequestDto {

    private String title;

    private String subTitle;

    private LocalDate startDate;

    private LocalTime time;

    private List<Integer> dayList;

    private List<Long> memberList;

}
