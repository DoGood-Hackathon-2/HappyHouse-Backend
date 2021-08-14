package com.example.happyhousebackend.domain.routine.dto.response;

import com.example.happyhousebackend.domain.member.dto.MemberResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class RoutineDetailDto {

    private String title;

    private String subTitle;

    private LocalDate startDate;

    private LocalTime time;

    private List<Integer> dayList;

    private MemberResponseDto familyList;

}
