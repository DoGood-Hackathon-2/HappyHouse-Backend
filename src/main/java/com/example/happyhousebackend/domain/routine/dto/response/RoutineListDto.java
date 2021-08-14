package com.example.happyhousebackend.domain.routine.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class RoutineListDto {

    private List<RoutineRepeatDto> repeatList;

    private List<RoutineRepeatDto> notRepeatList;

}
