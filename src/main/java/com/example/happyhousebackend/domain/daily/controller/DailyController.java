package com.example.happyhousebackend.domain.daily.controller;

import com.example.happyhousebackend.domain.daily.dto.DailyRequestDto;
import com.example.happyhousebackend.domain.daily.mapper.DailyMapper;
import com.example.happyhousebackend.domain.daily.service.DailyService;
import com.example.happyhousebackend.domain.member.entity.Member;
import com.example.happyhousebackend.domain.member.service.MemberService;
import com.example.happyhousebackend.global.message.ResponseMessage;
import com.example.happyhousebackend.global.message.SuccessMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class DailyController {

    private final MemberService memberService;
    private final DailyService dailyService;

    private final DailyMapper dailyMapper;

    @PostMapping("/{memberId}/daily")
    public ResponseEntity<ResponseMessage> saveDaily(@RequestBody DailyRequestDto requestDto, @PathVariable Long memberId) {
        Member member = memberService.findById(memberId); // 원래는 인증으로 빼야 함.
        dailyService.saveDaily(dailyMapper.toEntity(requestDto, member));
        return ResponseEntity.ok().body(ResponseMessage.of(SuccessMessage.SUCCESS_SAVE_DAILY));
    }

}
