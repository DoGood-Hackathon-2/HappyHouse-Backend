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
        return routineCompletedRepository.findRoutineCommentDtoList(title, member.getFamily().getId());
    }

    @Transactional
    public void deleteRoutine(Long routineId, Member member) {
        RoutineCompleted routine = routineCompletedRepository.findByRoutineIdAndMember(routineId, member)
                .orElseThrow(() -> new IllegalArgumentException("루틴이 존재하지 않습니다."));

        routineCompletedRepository.delete(routine);
    }

}
