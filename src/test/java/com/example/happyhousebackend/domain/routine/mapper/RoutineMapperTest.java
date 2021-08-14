package com.example.happyhousebackend.domain.routine.mapper;

import com.example.happyhousebackend.domain.routine.dto.RoutineRequestDto;
import com.example.happyhousebackend.domain.routine.entity.Routine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RoutineMapperTest {

    @Autowired
    private RoutineMapper routineMapper;


    private final String title = "테스트";
    private final String subTitle = "요청 메세지";
    private final LocalTime time = LocalTime.now();
    private final LocalDate startDate = LocalDate.now();
    private final List<Integer> dayList = List.of(1, 2, 3);
    private final List<Long> memberList = List.of(2L, 3L);

    final RoutineRequestDto requestDto = RoutineRequestDto.builder()
            .title(title)
            .subTitle(subTitle)
            .time(time)
            .startDate(startDate)
            .dayList(dayList)
            .memberList(memberList)
            .build();

    final Routine routine = Routine.builder()
            .id(null)
            .title(title)
            .subTitle(subTitle)
            .time(time)
            .startDate(startDate)
            .build();

    @Test
    void toEntity_테스트() {
        final Routine mappedEntity = routineMapper.toEntity(requestDto);

        assertThat(routine.getTitle(), is(mappedEntity.getTitle()));
        assertThat(routine.getSubTitle(), is(requestDto.getSubTitle()));
        assertThat(routine.getTime(), is(requestDto.getTime()));
        assertThat(routine.getStartDate(), is(requestDto.getStartDate()));
    }

}