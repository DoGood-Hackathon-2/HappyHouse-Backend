package com.example.happyhousebackend.domain.routine.repository;

import com.example.happyhousebackend.domain.routine.entity.Routine;
import com.example.happyhousebackend.domain.routine.entity.RoutineRepeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoutineRepeatRepository extends JpaRepository<RoutineRepeat, Long> {

    boolean existsAllByRoutine(Routine routine);

    List<RoutineRepeat> findAllByRoutine(Routine routine);

    void deleteRoutineRepeatsByRoutine(Routine routine);

}
