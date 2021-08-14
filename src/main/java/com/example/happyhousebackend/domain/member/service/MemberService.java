package com.example.happyhousebackend.domain.member.service;

import com.example.happyhousebackend.domain.family.entity.Family;
import com.example.happyhousebackend.domain.family.repository.FamilyRepository;
import com.example.happyhousebackend.domain.member.dto.MemberDto;
import com.example.happyhousebackend.domain.member.dto.MemberRequestDto;
import com.example.happyhousebackend.domain.member.dto.MemberResponseDto;
import com.example.happyhousebackend.domain.member.dto.MyMemberResponseDto;
import com.example.happyhousebackend.domain.member.entity.Member;
import com.example.happyhousebackend.domain.member.repository.MemberRepository;
import com.example.happyhousebackend.domain.routine.dto.response.RoutineListDto;
import com.example.happyhousebackend.domain.routine.service.RoutineRepeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final RoutineRepeatService routineRepeatService;

    private final FamilyRepository familyRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public Member findById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("id: " + memberId + " 인 회원이 존재하지 않습니다."));
    }

    @Transactional
    public void registerMember(MemberRequestDto requestDto, Long memberId) {
        Member member = findById(memberId);
        member.changeNickname(requestDto.getNickname());
        member.changeImage(requestDto.getImage());

        memberRepository.save(member);
    }

    @Transactional
    public MemberResponseDto findFamily(Long memberId) {
        Member member = findById(memberId);
        Family family = familyRepository.findById(member.getFamily().getId())
                .orElseThrow(() -> new IllegalArgumentException("가족 구성원이 존재하지 않습니다."));

        List<MemberDto> memberList = memberRepository.findAllByFamilyId(family.getId())
                .stream()
                .filter(m -> m != member)
                .map(Member::entityToDto)
                .collect(Collectors.toList());

        memberList.add(MemberDto.builder().id(memberId).image(member.getImage()).nickname("나").build());

        return MemberResponseDto.builder()
                .memberList(memberList)
                .build();
    }

    @Transactional
    public MyMemberResponseDto findMyPage(Long memberId) {
        Member me = findById(memberId);
        Family family = familyRepository.findById(me.getFamily().getId())
                .orElseThrow(() -> new IllegalArgumentException("가족 구성원이 존재하지 않습니다."));

        String familyName = family.getName();
        String nickname = "나";

        List<MemberDto> memberList = memberRepository.findAllByFamilyId(family.getId())
                .stream()
                .map(Member::entityToDto)
                .collect(Collectors.toList());

        RoutineListDto routineList = routineRepeatService.getRoutineRepeatList(me);
        return MyMemberResponseDto.builder()
                .familyName(familyName)
                .nickname(nickname)
                .routineRate(routineList.getRepeatList().size() + routineList.getNotRepeatList().size())
                .memberList(memberList)
                .routine(routineList)
                .build();
    }
}
