package com.example.happyhousebackend.domain.routine.repository;

import com.example.happyhousebackend.domain.member.entity.Member;
import com.example.happyhousebackend.domain.routine.dto.response.RoutineCommentDto;
import com.example.happyhousebackend.domain.routine.entity.RoutineCompleted;
import com.example.happyhousebackend.domain.routine.entity.RoutineCompletedPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RoutineCompletedRepository extends JpaRepository<RoutineCompleted, RoutineCompletedPK> {

    List<RoutineCompleted> findRoutineCompletedsByIdRoutineId(Long routineId);

    List<RoutineCompleted> findRoutineCompletedByMemberId(Long memberId);

    Optional<RoutineCompleted> findByRoutineIdAndMember(Long routineId, Member member);

    @Query(value = "SELECT new com.example.happyhousebackend.domain.routine.dto.response.RoutineCommentDto(rc.member.nickname, rc.member.image, rc.image, rc.comment, rc.createdDate) " +
                   "FROM RoutineCompleted rc " +
                   "LEFT JOIN Routine r ON r.title=?1 AND r.id=rc.routine.id WHERE rc.family.id=?2 AND rc.isCompleted=true")
    List<RoutineCommentDto> findRoutineCommentDtoList(String title, Long familyId);

}
