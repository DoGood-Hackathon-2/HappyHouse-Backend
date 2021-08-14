package com.example.happyhousebackend.domain.family.service;

import com.example.happyhousebackend.domain.family.dto.FamilyNameRequestDto;
import com.example.happyhousebackend.domain.member.entity.Member;
import com.example.happyhousebackend.domain.member.service.MemberService;
import com.example.happyhousebackend.domain.util.ResponseMessage;
import com.example.happyhousebackend.domain.family.entity.Family;
import com.example.happyhousebackend.domain.family.repository.FamilyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@RequiredArgsConstructor
@Service
public class FamilyService {

    private final FamilyRepository familyRepository;
    private final MemberService memberService;

    @Transactional
    public ResponseMessage registerFamilyName(FamilyNameRequestDto requestDto, Long memberId) {
        Assert.notNull(requestDto.getName(), "family name must not be null!");
        Assert.notNull(memberId, "memberId must not be null!");

        Member member = memberService.findById(memberId);
        Family family = Family.builder()
                .name(requestDto.getName())
                .build();

        member.setFamily(family);
        familyRepository.save(family);

        return ResponseMessage.of("정상", family.getId());
    }
}
