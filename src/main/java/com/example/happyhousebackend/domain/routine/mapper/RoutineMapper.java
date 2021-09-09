package com.example.happyhousebackend.domain.routine.mapper;

import com.example.happyhousebackend.domain.member.dto.MemberResponseDto;
import com.example.happyhousebackend.domain.member.entity.Member;
import com.example.happyhousebackend.domain.routine.dto.request.RoutineRequestDto;
import com.example.happyhousebackend.domain.routine.dto.request.RoutineUpdateRequestDto;
import com.example.happyhousebackend.domain.routine.dto.response.RoutineDetailDto;
import com.example.happyhousebackend.domain.routine.entity.Routine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoutineMapper {

    @Mapping(target = "family", expression = "java(member.getFamily())")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    Routine toEntity(RoutineRequestDto requestDto, Member member);

    @Mapping(target = "id", source = "routineId")
    @Mapping(target = "family", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    Routine toEntity(Long routineId, RoutineUpdateRequestDto requestDto, Member member);

    RoutineDetailDto toDto(Routine routine, List<Integer> dayList, MemberResponseDto familyList);
}
