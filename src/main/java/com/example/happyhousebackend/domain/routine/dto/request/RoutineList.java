package com.example.happyhousebackend.domain.routine.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RoutineList {

    private String title;

    private String subTitle;

    private LocalDate startDate;

    private List<String> imageList;
}
