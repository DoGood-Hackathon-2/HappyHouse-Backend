package com.example.happyhousebackend.domain.routine.repository;

import com.example.happyhousebackend.domain.routine.entity.SampleRoutine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SampleRoutineRepository extends JpaRepository<SampleRoutine, Long> {
}
