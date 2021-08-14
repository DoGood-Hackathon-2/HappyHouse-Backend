package com.example.happyhousebackend.domain.family.controller;

import com.example.happyhousebackend.domain.family.dto.FamilyNameRequestDto;
import com.example.happyhousebackend.domain.util.ResponseMessage;
import com.example.happyhousebackend.domain.family.service.FamilyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.happyhousebackend.domain.util.SuccessMessage.SUCCESS_SAVE_FAMILY;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class FamilyController {

    private final FamilyService familyService;

    @PostMapping("/{memberId}/family")
    public ResponseEntity<ResponseMessage> registerFamilyName(@RequestBody FamilyNameRequestDto requestDto, @PathVariable Long memberId) {
        return ResponseEntity.ok().body(ResponseMessage.of(SUCCESS_SAVE_FAMILY, familyService.registerFamilyName(requestDto, memberId)));
    }

}
