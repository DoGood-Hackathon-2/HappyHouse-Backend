package com.example.happyhousebackend.domain.routine.service;

import com.example.happyhousebackend.domain.member.entity.Member;
import com.example.happyhousebackend.domain.member.repository.MemberRepository;
import com.example.happyhousebackend.domain.routine.dto.response.RoutineCommentDto;
import com.example.happyhousebackend.domain.routine.entity.Routine;
import com.example.happyhousebackend.domain.routine.entity.RoutineCompleted;
import com.example.happyhousebackend.domain.routine.entity.RoutineCompletedPK;
import com.example.happyhousebackend.domain.routine.repository.RoutineCompletedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
                    .family(routine.getFamily())
                    .build();

            routineCompletedRepository.save(routineCompleted);
        });
    }

    @Transactional(readOnly = true)
    public List<RoutineCommentDto> getRoutineCommentList(String title, Member member) {
        List<Object[]> resultList = routineCompletedRepository.findTest(title, member.getFamily().getId());
        List<RoutineCommentDto> routineList = new ArrayList<>();
        resultList.forEach(objects ->
                routineList.add(RoutineCommentDto.builder()
                        .nickname((String) objects[0])
                        .memberImage((String) objects[1])
                        .routineImage((String) objects[2])
                        .comment((String) objects[3])
                        .build())
        );
        return routineList;
    }

}
