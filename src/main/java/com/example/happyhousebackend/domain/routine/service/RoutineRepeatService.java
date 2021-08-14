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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RoutineRepeatService {

    private final RoutineRepeatRepository routineRepeatRepository;
    private final RoutineCompletedRepository routineCompletedRepository;

    public void saveRepeatDay(Routine routine, List<Integer> dayList) {
        dayList.forEach(day -> routineRepeatRepository.save(
                RoutineRepeat.builder()
                        .routine(routine)
                        .day(day)
                        .build())
        );
    }

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
                                .dayList(routine.getRoutineRepeatList().stream().map(RoutineRepeat::getDay).collect(Collectors.toList()))
                                .subTitle(routine.getSubTitle())
                                .time(routine.getTime())
                                .requesterImage(routine.getMember().getImage())
                                .memberList(routine.getRoutineCompletedList().stream().map(RoutineCompleted::getMember).map(Member::getImage).collect(Collectors.toList()))
                                .date(routine.getStartDate())
                                .build()
                        );
                    }
                    else {
                        notRepeatRoutineList.add(RoutineRepeatDto.builder()
                                .id(routine.getId())
                                .title(routine.getTitle())
                                .subTitle(routine.getSubTitle())
                                .time(routine.getTime())
                                .requesterImage(routine.getMember().getImage())
                                .memberList(routine.getRoutineCompletedList().stream().map(RoutineCompleted::getMember).map(Member::getImage).collect(Collectors.toList()))
                                .date(routine.getStartDate())
                                .build()
                        );
                    }
                });

        return new RoutineListDto(repeatRoutineList, notRepeatRoutineList);

    }
}
