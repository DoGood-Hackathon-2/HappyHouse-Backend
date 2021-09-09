package com.example.happyhousebackend.domain.routine.service;

import com.example.happyhousebackend.domain.member.entity.Member;
import com.example.happyhousebackend.domain.routine.dto.response.RoutineListDto;
import com.example.happyhousebackend.domain.routine.dto.response.RoutineRepeatDto;
import com.example.happyhousebackend.domain.routine.entity.Routine;
import com.example.happyhousebackend.domain.routine.entity.RoutineCompleted;
import com.example.happyhousebackend.domain.routine.entity.RoutineRepeat;
import com.example.happyhousebackend.domain.routine.repository.RoutineCompletedRepository;
import com.example.happyhousebackend.domain.routine.repository.RoutineRepeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class RoutineRepeatService {

    private final RoutineRepeatRepository routineRepeatRepository;
    private final RoutineCompletedRepository routineCompletedRepository;

    @Transactional
    public void saveRepeatDay(Routine routine, List<Integer> dayList) {
        dayList.forEach(day -> routineRepeatRepository.save(
                RoutineRepeat.builder()
                        .routine(routine)
                        .day(day)
                        .build())
        );
    }

    @Transactional
    public void updateRepeatDay(Routine routine, List<Integer> dayList) {
        routineRepeatRepository.deleteRoutineRepeatsByRoutine(routine);
        saveRepeatDay(routine, dayList);
    }

    @Transactional(readOnly = true)
    public List<Integer> getDayList(Routine routine) {
        return routineRepeatRepository.findAllByRoutine(routine).stream().map(RoutineRepeat::getDay).collect(toList());
    }

    @Transactional(readOnly = true)
    public RoutineListDto getRoutineRepeatList(Member member) {
        List<RoutineRepeatDto> repeatRoutineList = new ArrayList<>();
        List<RoutineRepeatDto> notRepeatRoutineList = new ArrayList<>();

        List<RoutineCompleted> routineList = routineCompletedRepository.findRoutineCompletedByMemberId(member.getId());
        routineList.stream().map(RoutineCompleted::getRoutine)
                .forEach(routine -> {
                    if (routineRepeatRepository.existsAllByRoutine(routine)) {
                        repeatRoutineList.add(RoutineRepeatDto.builder()
                                .id(routine.getId())
                                .title(routine.getTitle())
                                .dayList(routine.getRoutineRepeatList().stream().map(RoutineRepeat::getDay).collect(toList()))
                                .subTitle(routine.getSubTitle())
                                .time(routine.getTime())
                                .requesterImage(routine.getMember().getImage())
                                .memberList(routine.getRoutineCompletedList().stream().map(RoutineCompleted::getMember).map(Member::getImage).collect(toList()))
                                .date(routine.getStartDate())
                                .build()
                        );
                    } else {
                        notRepeatRoutineList.add(RoutineRepeatDto.builder()
                                .id(routine.getId())
                                .title(routine.getTitle())
                                .subTitle(routine.getSubTitle())
                                .time(routine.getTime())
                                .requesterImage(routine.getMember().getImage())
                                .memberList(routine.getRoutineCompletedList().stream().map(RoutineCompleted::getMember).map(Member::getImage).collect(toList()))
                                .date(routine.getStartDate())
                                .build()
                        );
                    }
                });

        return new RoutineListDto(repeatRoutineList, notRepeatRoutineList);
    }

}
