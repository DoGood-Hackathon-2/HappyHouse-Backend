package com.example.happyhousebackend.domain.routine.entity;

import com.example.happyhousebackend.domain.family.entity.Family;
import com.example.happyhousebackend.domain.member.entity.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Builder.Default
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
