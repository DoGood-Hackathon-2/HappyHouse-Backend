package com.example.happyhousebackend.domain.routine.controller;

import com.example.happyhousebackend.domain.member.entity.Member;
import com.example.happyhousebackend.domain.member.service.MemberService;
import com.example.happyhousebackend.domain.routine.controller.dto.RoutineCreateDto;
import com.example.happyhousebackend.domain.routine.dto.RoutineCommentRequestDto;
import com.example.happyhousebackend.domain.routine.dto.RoutineRequestDto;
import com.example.happyhousebackend.domain.routine.mapper.RoutineMapper;
import com.example.happyhousebackend.domain.routine.service.RoutineCompletedService;
import com.example.happyhousebackend.domain.routine.service.RoutineService;
import com.example.happyhousebackend.domain.util.ResponseMessage;
import com.example.happyhousebackend.domain.util.SuccessMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class RoutineController {

    private final RoutineService routineService;
    private final RoutineCompletedService routineCompletedService;
    private final MemberService memberService;

    private final RoutineMapper routineMapper;

    @PostMapping("/{memberId}/routine")
    public ResponseEntity<ResponseMessage> saveRoutine(@RequestBody RoutineRequestDto requestDto, @PathVariable Long memberId) {
        Member member = memberService.findById(memberId); // 원래는 인증으로 빼야 함.
        requestDto.getMemberList().add(memberId);
        routineService.saveAllRoutine(routineMapper.toEntity(requestDto, member), requestDto.getDayList(), requestDto.getMemberList());
        return ResponseEntity.ok().body(ResponseMessage.of(SuccessMessage.SUCCESS_SAVE_ROUTINE));
    }

    @GetMapping("/{memberId}/routines")
    public ResponseEntity<ResponseMessage> getRoutineCommentList(@RequestBody RoutineCommentRequestDto requestDto, @PathVariable Long memberId) {
        Member member = memberService.findById(memberId);
        return ResponseEntity.ok().body(ResponseMessage.of(SuccessMessage.SUCCESS_GET_ROUTINE, routineCompletedService.getRoutineCommentList(requestDto.getTitle(), member)));
    }

    @GetMapping("/{memberId}/routines/{routineId}")
    public ResponseEntity<ResponseMessage> getRoutineDetail(@PathVariable Long memberId, @PathVariable Long routineId) {
        Member member = memberService.findById(memberId);
        return ResponseEntity.ok().body(ResponseMessage.of(SuccessMessage.SUCCESS_GET_ROUTINE, routineService.getRoutineDetail(routineId, member)));
    }

    @PostMapping("/{memberId}/routine/{routineId}/complete")
    public ResponseEntity<ResponseMessage> completeRoutine(@PathVariable Long memberId, @PathVariable Long routineId, @RequestBody RoutineCreateDto createDto) {
        routineService.createRoutine(memberId, routineId, createDto);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseMessage.of(SuccessMessage.SUCCESS_COMPLETE_ROUTINE));
    }
}
