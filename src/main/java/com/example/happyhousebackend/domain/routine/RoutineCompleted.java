package com.example.happyhousebackend.domain.routine;

import com.example.happyhousebackend.domain.member.Member;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

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
    @ColumnDefault(value = "0")
    private boolean isCompleted;

}
