package com.example.happyhousebackend.domain.member.service;

import com.example.happyhousebackend.domain.family.entity.Family;
import com.example.happyhousebackend.domain.family.repository.FamilyRepository;
import com.example.happyhousebackend.domain.member.dto.MyMemberResponseDto;
import com.example.happyhousebackend.domain.member.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private FamilyRepository familyRepository;

    @Test
    @Transactional
    void 마이페이지_테스트() {
        // given
        Member me = memberService.findById(1L);
        Family family = familyRepository.findById(me.getFamily().getId())
                .orElseThrow(() -> new IllegalArgumentException("가족 구성원이 존재하지 않습니다."));

        // when
        MyMemberResponseDto dto = memberService.findMyPage(1L);

        // then
        assertThat(dto.getFamilyName()).isEqualTo(family.getName());
    }

}
