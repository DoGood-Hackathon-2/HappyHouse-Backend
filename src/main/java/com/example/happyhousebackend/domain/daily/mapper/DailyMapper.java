package com.example.happyhousebackend.domain.daily.mapper;

import com.example.happyhousebackend.domain.daily.dto.DailyRequestDto;
import com.example.happyhousebackend.domain.daily.entity.Daily;
import com.example.happyhousebackend.domain.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DailyMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    Daily toEntity(DailyRequestDto requestDto, Member member);

}
