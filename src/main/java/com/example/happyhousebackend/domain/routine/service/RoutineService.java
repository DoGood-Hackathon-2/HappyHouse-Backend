package com.example.happyhousebackend.domain.routine.service;

import com.example.happyhousebackend.domain.routine.controller.dto.RoutineCreateDto;
import com.example.happyhousebackend.domain.routine.entity.Routine;
import com.example.happyhousebackend.domain.routine.entity.RoutineCompleted;
import com.example.happyhousebackend.domain.routine.entity.RoutineCompletedPK;
import com.example.happyhousebackend.domain.routine.repository.RoutineCompletedRepository;
import com.example.happyhousebackend.domain.routine.repository.RoutineRepository;
import com.example.happyhousebackend.domain.util.ResponseMessage;
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

    private final RoutineCompletedRepository routineCompletedRepository;

    @Transactional
    public void saveAllRoutine(Routine routine, List<Integer> dayList, List<Long> memberList) {
        // routine 등록
        saveRoutine(routine);
        // 반복 요일 등록
        routineRepeatService.saveRepeatDay(routine, dayList);
        // 루틴 구성원 등록
        routineCompletedService.saveRoutineMemberList(routine, memberList);

    }

    private void saveRoutine(Routine routine) {
        routineRepository.save(routine);
    }

    @Transactional
    public void createRoutine(Long memberId, Long routineId, RoutineCreateDto createDto) {
        RoutineCompletedPK routineCompletedPK = new RoutineCompletedPK(routineId, memberId);

        RoutineCompleted routineCompleted = routineCompletedRepository.findById(routineCompletedPK)
                .orElseThrow(() -> new IllegalArgumentException("루틴_회원이 존재하지 않습니다."));
        routineCompleted.setCompleted(true);
        routineCompleted.setImage(createDto.getImage());
        routineCompleted.setComment(createDto.getComment());

        Routine routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new IllegalArgumentException("루틴이 존재하지 않습니다."));

        routineCompletedRepository.save(routineCompleted);
        routineRepository.save(routine);
    }


}
