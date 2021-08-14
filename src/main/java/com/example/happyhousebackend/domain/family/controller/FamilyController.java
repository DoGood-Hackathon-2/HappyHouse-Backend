package com.example.happyhousebackend.domain.family.controller;

import com.example.happyhousebackend.domain.family.controller.dto.FamilyNameRequestDto;
import com.example.happyhousebackend.domain.util.ResponseMessage;
import com.example.happyhousebackend.domain.family.service.FamilyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class FamilyController {

    private final FamilyService familyService;

    @PostMapping("/{memberId}/family")
    public ResponseEntity<ResponseMessage> registerFamilyName(@RequestBody FamilyNameRequestDto requestDto, @PathVariable Long memberId) {
        ResponseMessage responseMessage = familyService.registerFamilyName(requestDto, memberId);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

}
