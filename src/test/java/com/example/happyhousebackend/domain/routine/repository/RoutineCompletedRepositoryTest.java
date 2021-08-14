package com.example.happyhousebackend.domain.routine.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoutineCompletedRepositoryTest {
    
    @Autowired
    private RoutineCompletedRepository routineCompletedRepository;
    
    // @Test
    void sql_테스트() {
        List<Object[]> list = routineCompletedRepository.findTest("3분 산책", 2L);
        list.forEach(o -> System.out.println(o[0]));
    }
}