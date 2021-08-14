package com.example.happyhousebackend.domain.routine.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Embeddable
public class RoutineCompletedPK implements Serializable {

    private Long routineId;
    private Long memberId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoutineCompletedPK that = (RoutineCompletedPK) o;
        return Objects.equals(routineId, that.routineId) && Objects.equals(memberId, that.memberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(routineId, memberId);
    }

    @Builder
    public RoutineCompletedPK(Long routineId, Long memberId) {
        this.routineId = routineId;
        this.memberId = memberId;
    }

}
