package com.example.happyhousebackend.domain.routine.controller;

import com.example.happyhousebackend.domain.member.entity.Member;
import com.example.happyhousebackend.domain.member.service.MemberService;
import com.example.happyhousebackend.domain.routine.dto.RoutineRequestDto;
import com.example.happyhousebackend.domain.routine.mapper.RoutineMapper;
import com.example.happyhousebackend.domain.routine.service.RoutineService;
import com.example.happyhousebackend.domain.util.ResponseMessage;
import com.example.happyhousebackend.domain.util.SuccessMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class RoutineController {

    private final RoutineService routineService;
    private final MemberService memberService;

    private final RoutineMapper routineMapper;

    @PostMapping("/{memberId}/routine")
    public ResponseEntity<ResponseMessage> saveRoutine(@RequestBody RoutineRequestDto requestDto, @PathVariable Long memberId) {
        Member member = memberService.findById(memberId); // 원래는 인증으로 빼야 함.
        requestDto.getMemberList().add(memberId);
        routineService.saveAllRoutine(routineMapper.toEntity(requestDto, member), requestDto.getDayList(), requestDto.getMemberList());
        return ResponseEntity.ok().body(ResponseMessage.of(SuccessMessage.SUCCESS_SAVE_ROUTINE));
    }

}
