package com.example.happyhousebackend.domain.routine.controller;

import com.example.happyhousebackend.domain.member.entity.Member;
import com.example.happyhousebackend.domain.member.service.MemberService;
import com.example.happyhousebackend.domain.routine.dto.request.RoutineCreateDto;
import com.example.happyhousebackend.domain.routine.dto.request.RoutineCommentRequestDto;
import com.example.happyhousebackend.domain.routine.dto.request.RoutineRequestDto;
import com.example.happyhousebackend.domain.routine.dto.request.RoutineUpdateRequestDto;
import com.example.happyhousebackend.domain.routine.mapper.RoutineMapper;
import com.example.happyhousebackend.domain.routine.service.RoutineCompletedService;
import com.example.happyhousebackend.domain.routine.service.RoutineService;
import com.example.happyhousebackend.global.message.ResponseMessage;
import com.example.happyhousebackend.global.message.SuccessMessage;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/{memberId}/routines")
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

    @PutMapping("/{memberId}/routines/{routineId}") // 멤버 변경 X
    public ResponseEntity<ResponseMessage> updateRoutine(@PathVariable Long memberId, @PathVariable Long routineId, @RequestBody RoutineUpdateRequestDto requestDto) {
        Member member = memberService.findById(memberId);
        routineService.updateAllRoutine(routineMapper.toEntity(routineId, requestDto, member), requestDto.getDayList());
        return ResponseEntity.ok().body(ResponseMessage.of(SuccessMessage.SUCCESS_UPDATE));
    }

    @PostMapping("/{memberId}/routines/{routineId}/complete")
    public ResponseEntity<ResponseMessage> completeRoutine(@PathVariable Long memberId, @PathVariable Long routineId, @RequestBody RoutineCreateDto createDto) {
        routineService.createRoutine(memberId, routineId, createDto);
        return ResponseEntity.ok().body(ResponseMessage.of(SuccessMessage.SUCCESS_COMPLETE_ROUTINE));
    }
}
