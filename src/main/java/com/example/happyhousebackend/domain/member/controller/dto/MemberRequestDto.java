package com.example.happyhousebackend.domain.member.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MemberRequestDto {

    private Long familyId;
    private String nickname;
    private String image;
}
