package com.example.happyhousebackend.domain.routine.service;

import com.example.happyhousebackend.domain.member.repository.MemberRepository;
import com.example.happyhousebackend.domain.routine.entity.Routine;
import com.example.happyhousebackend.domain.routine.entity.RoutineCompleted;
import com.example.happyhousebackend.domain.routine.entity.RoutineCompletedPK;
import com.example.happyhousebackend.domain.routine.repository.RoutineCompletedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RoutineCompletedService {

    private final RoutineCompletedRepository routineCompletedRepository;
    private final MemberRepository memberRepository;

    public void saveRoutineMemberList(Routine routine, List<Long> memberList) {
        memberList.forEach(memberId -> {
            RoutineCompletedPK routineCompletedPK = RoutineCompletedPK.builder()
                    .routineId(routine.getId())
                    .memberId(memberId)
                    .build();

            RoutineCompleted routineCompleted = RoutineCompleted.builder()
                    .id(routineCompletedPK)
                    .member(memberRepository.getById(memberId))
                    .routine(routine)
                    .build();

            routineCompletedRepository.save(routineCompleted);
        });
    }
}
