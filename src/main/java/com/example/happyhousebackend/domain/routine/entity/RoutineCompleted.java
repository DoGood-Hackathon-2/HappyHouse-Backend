package com.example.happyhousebackend.domain.routine.entity;

import com.example.happyhousebackend.domain.family.entity.Family;
import com.example.happyhousebackend.domain.member.entity.Member;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "routine_completed")
public class RoutineCompleted {

    @EmbeddedId
    private RoutineCompletedPK id;

    @MapsId("routineId")
    @ManyToOne
    @JoinColumn(name = "routine_id")
    private Routine routine;

    @MapsId("memberId")
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "is_completed")
    private boolean isCompleted;

    @ManyToOne
    @JoinColumn(name = "family_id")
    private Family family;

    private String image;

    private String comment;

}
