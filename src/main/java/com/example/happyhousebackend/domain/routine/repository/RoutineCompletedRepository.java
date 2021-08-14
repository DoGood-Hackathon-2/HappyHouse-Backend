package com.example.happyhousebackend.domain.routine.repository;

import com.example.happyhousebackend.domain.routine.entity.RoutineCompleted;
import com.example.happyhousebackend.domain.routine.entity.RoutineCompletedPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoutineCompletedRepository extends JpaRepository<RoutineCompleted, RoutineCompletedPK> {

    List<RoutineCompleted> findRoutineCompletedsByIdRoutineId(Long routineId);

    @Query(value = "SELECT rc.member.nickname, rc.member.image, rc.image, rc.comment " +
                   "FROM RoutineCompleted rc " +
                   "LEFT JOIN Routine r ON r.title=?1 AND r.id=rc.routine.id WHERE rc.family.id=?2 AND rc.isCompleted=true")
    List<Object[]> findTest(String title, Long familyId);
}
