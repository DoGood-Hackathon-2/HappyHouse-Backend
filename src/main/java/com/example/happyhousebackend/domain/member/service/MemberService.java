package com.example.happyhousebackend.domain.member.service;

import com.example.happyhousebackend.domain.family.entity.Family;
import com.example.happyhousebackend.domain.family.repository.FamilyRepository;
import com.example.happyhousebackend.domain.member.controller.dto.MemberList;
import com.example.happyhousebackend.domain.member.controller.dto.MemberRequestDto;
import com.example.happyhousebackend.domain.member.controller.dto.MemberResponseDto;
import com.example.happyhousebackend.domain.member.entity.Member;
import com.example.happyhousebackend.domain.member.repository.MemberRepository;
import com.example.happyhousebackend.domain.util.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final FamilyRepository familyRepository;
    private final MemberRepository memberRepository;

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
}
