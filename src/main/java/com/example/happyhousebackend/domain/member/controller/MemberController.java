package com.example.happyhousebackend.domain.member.controller;

import com.example.happyhousebackend.domain.member.dto.MemberRequestDto;
import com.example.happyhousebackend.domain.member.dto.MemberResponseDto;
import com.example.happyhousebackend.domain.member.service.MemberService;
import com.example.happyhousebackend.domain.util.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.happyhousebackend.domain.util.SuccessMessage.SUCCESS_GET_MY_PAGE;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class MemberController {

    private final MemberService memberService;

    @PatchMapping("/member/{memberId}")
    public ResponseEntity<ResponseMessage> registerMember(@RequestBody MemberRequestDto requestDto, @PathVariable Long memberId) {
        ResponseMessage responseMessage = memberService.registerMember(requestDto, memberId);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @GetMapping("/{memberId}/family")
    public ResponseEntity<MemberResponseDto> findFamily(@PathVariable Long memberId) {
        MemberResponseDto dto = memberService.findFamily(memberId);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @GetMapping("/{memberId}/mypage")
    public ResponseEntity<ResponseMessage> findMyPage(@PathVariable Long memberId) {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseMessage.of(SUCCESS_GET_MY_PAGE, memberService.findMyPage(memberId)));
    }

}
