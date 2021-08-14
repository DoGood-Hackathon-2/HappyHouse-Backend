package com.example.happyhousebackend.domain.member.controller.dto;

import com.example.happyhousebackend.domain.routine.controller.dto.RoutineList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class MyMemberResponseDto {

    private String message;

    private String familyName;

    private String nickname;

    private int routineRate;

    private List<MemberList> memberList;

    private List<RoutineList> notCompleteRoutineList;

    private List<RoutineList> completeRoutineList;

}
