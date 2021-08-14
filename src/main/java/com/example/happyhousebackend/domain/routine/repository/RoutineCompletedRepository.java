package com.example.happyhousebackend.domain.routine.repository;

import com.example.happyhousebackend.domain.routine.entity.RoutineCompleted;
import com.example.happyhousebackend.domain.routine.entity.RoutineCompletedPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoutineCompletedRepository extends JpaRepository<RoutineCompleted, RoutineCompletedPK> {
}
