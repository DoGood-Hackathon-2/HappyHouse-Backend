package com.example.happyhousebackend.domain.routine.service;

import com.example.happyhousebackend.domain.member.entity.Member;
import com.example.happyhousebackend.domain.member.repository.MemberRepository;
import com.example.happyhousebackend.domain.routine.entity.Routine;
import com.example.happyhousebackend.domain.routine.entity.RoutineCompleted;
import com.example.happyhousebackend.domain.routine.repository.RoutineCompletedRepository;
import com.example.happyhousebackend.domain.routine.repository.RoutineRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RoutineCompletedServiceTest {

    @Autowired
    private RoutineCompletedService routineCompletedService;

    @Autowired
    private RoutineCompletedRepository routineCompletedRepository;

    @Autowired
    private RoutineRepository routineRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 루틴_회원_추가_테스트() {
        Routine routine = Routine.builder()
                .title("테스트")
                .subTitle("요청 메세지")
                .build();

        routineRepository.save(routine);

        Member member1 = Member.builder().build();
        Member member2 = Member.builder().build();
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Long> memberList = List.of(member1.getId(), member2.getId());

        // when
        routineCompletedService.saveRoutineMemberList(routine, memberList);

        // then
        List<RoutineCompleted> routineCompletedList = routineCompletedRepository.findRoutineCompletedsByIdRoutineId(routine.getId());
        assertEquals(routineCompletedList.size(), 2);

    }

}