package com.example.happyhousebackend.domain.member.service;

import com.example.happyhousebackend.domain.family.entity.Family;
import com.example.happyhousebackend.domain.family.repository.FamilyRepository;
import com.example.happyhousebackend.domain.member.controller.dto.MemberList;
import com.example.happyhousebackend.domain.member.controller.dto.MemberRequestDto;
import com.example.happyhousebackend.domain.member.controller.dto.MemberResponseDto;
import com.example.happyhousebackend.domain.member.controller.dto.MyMemberRequestDto;
import com.example.happyhousebackend.domain.member.controller.dto.MyMemberResponseDto;
import com.example.happyhousebackend.domain.member.entity.Member;
import com.example.happyhousebackend.domain.member.repository.MemberRepository;
import com.example.happyhousebackend.domain.routine.controller.dto.RoutineList;
import com.example.happyhousebackend.domain.routine.entity.Routine;
import com.example.happyhousebackend.domain.routine.entity.RoutineCompleted;
import com.example.happyhousebackend.domain.routine.repository.RoutineCompletedRepository;
import com.example.happyhousebackend.domain.util.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final FamilyRepository familyRepository;
    private final MemberRepository memberRepository;
    private final RoutineCompletedRepository routineCompletedRepository;

    @Transactional(readOnly = true)
    public Member findById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("id: " + memberId + " 인 회원이 존재하지 않습니다."));
    }

    @Transactional
    public ResponseMessage registerMember(MemberRequestDto requestDto, Long memberId) {
        Member member = findById(memberId);
        member.changeNickname(requestDto.getNickname());
        member.changeImage(requestDto.getImage());

        memberRepository.save(member);
        return ResponseMessage.of("정상");
    }

    @Transactional
    public MemberResponseDto findFamily(Long memberId) {
        Member member = findById(memberId);
        Family family = familyRepository.findById(member.getFamily().getId())
                .orElseThrow(() -> new IllegalArgumentException("가족 구성원이 존재하지 않습니다."));

        List<MemberList> memberList = memberRepository.findAllByFamilyId(family.getId())
                .stream()
                .map(Member::entityToDto)
                .collect(Collectors.toList());

        return MemberResponseDto.builder()
                .message("정상")
                .memberList(memberList)
                .build();
    }

    @Transactional
    public MyMemberResponseDto findMyPage(Long memberId, MyMemberRequestDto requestDto) {
        Member me = findById(memberId);
        Family family = familyRepository.findById(me.getFamily().getId())
                .orElseThrow(() -> new IllegalArgumentException("가족 구성원이 존재하지 않습니다."));

        String familyName = family.getName();
        String nickname = "나";

        List<MemberList> memberList = memberRepository.findAllByFamilyId(family.getId())
                .stream()
                .map(Member::entityToDto)
                .collect(Collectors.toList());

        List<RoutineCompleted> routineCompletedList = routineCompletedRepository.findRoutineCompletedByMemberId(memberId);
        List<RoutineList> notCompleteRoutineList = new ArrayList<>();
        List<RoutineList> completeRoutineList = new ArrayList<>();

        for (int i = 0; i < routineCompletedList.size(); i++) {
            Routine routine = routineCompletedList.get(i).getRoutine();
            RoutineList routineList = RoutineList.builder()
                    .title(routine.getTitle())
                    .subTitle(routine.getSubTitle())
                    .startDate(routine.getStartDate())
                    .build();

            if (routineCompletedList.get(i).isCompleted()) {
                completeRoutineList.add(routineList);
            } else {
                notCompleteRoutineList.add(routineList);
            }
        }
        return MyMemberResponseDto.builder()
                .message("정상")
                .familyName(familyName)
                .nickname(nickname)
                .routineRate(routineCompletedList.size() + completeRoutineList.size())
                .memberList(memberList)
                .notCompleteRoutineList(notCompleteRoutineList)
                .completeRoutineList(completeRoutineList)
                .build();
    }
}
