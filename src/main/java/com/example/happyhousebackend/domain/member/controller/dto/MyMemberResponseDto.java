package com.example.happyhousebackend.domain.member.controller.dto;

import com.example.happyhousebackend.domain.routine.dto.response.RoutineListDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MyMemberResponseDto {

    private String familyName;

    private String nickname;

    private int routineRate;

    private List<MemberList> memberList;

    private RoutineListDto routine;

}
