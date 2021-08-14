package com.example.happyhousebackend.domain.routine.service;

import com.example.happyhousebackend.domain.routine.entity.Routine;
import com.example.happyhousebackend.domain.routine.entity.RoutineRepeat;
import com.example.happyhousebackend.domain.routine.repository.RoutineRepeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RoutineRepeatService {

    private final RoutineRepeatRepository routineRepeatRepository;

    public void saveRepeatDay(Routine routine, List<Integer> dayList) {
        dayList.forEach(day -> routineRepeatRepository.save(
                RoutineRepeat.builder()
                        .routine(routine)
                        .day(day)
                        .build())
        );
    }
}
