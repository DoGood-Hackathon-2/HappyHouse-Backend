package com.example.happyhousebackend.domain.routine.mapper;

import com.example.happyhousebackend.domain.member.entity.Member;
import com.example.happyhousebackend.domain.routine.dto.RoutineRequestDto;
import com.example.happyhousebackend.domain.routine.entity.Routine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoutineMapper {

    @Mapping(target = "id", ignore = true)
    Routine toEntity(RoutineRequestDto requestDto, Member member);

}
