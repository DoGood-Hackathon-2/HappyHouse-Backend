package com.example.happyhousebackend.domain.routine.service;

import com.example.happyhousebackend.domain.routine.entity.Routine;
import com.example.happyhousebackend.domain.routine.repository.RoutineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RoutineService {

    private final RoutineRepository routineRepository;

    private final RoutineRepeatService routineRepeatService;

    private final RoutineCompletedService routineCompletedService;

    @Transactional
    public void saveAllRoutine(Routine routine, List<Integer> dayList, List<Long> memberList) {
        // routine 등록
        saveRoutine(routine);
        // 반복 요일 등록
        routineRepeatService.saveRepeatDay(routine, dayList);
        // 루틴 구성원 등록
        routineCompletedService.saveRoutineMemberList(routine, memberList);

    }

    public void saveRoutine(Routine routine) {
        routineRepository.save(routine);
    }


}
