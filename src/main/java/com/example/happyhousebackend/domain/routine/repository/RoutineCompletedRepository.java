package com.example.happyhousebackend.domain.routine.repository;

import com.example.happyhousebackend.domain.routine.entity.RoutineCompleted;
import com.example.happyhousebackend.domain.routine.entity.RoutineCompletedPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoutineCompletedRepository extends JpaRepository<RoutineCompleted, RoutineCompletedPK> {
    List<RoutineCompleted> findRoutineCompletedsByIdRoutineId(Long routineId);

    List<RoutineCompleted> findRoutineCompletedByMemberId(Long memberId);
}
