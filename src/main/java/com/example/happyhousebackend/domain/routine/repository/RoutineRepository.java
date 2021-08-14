package com.example.happyhousebackend.domain.routine.repository;

import com.example.happyhousebackend.domain.routine.entity.Routine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoutineRepository extends JpaRepository<Routine, Long> {
}
