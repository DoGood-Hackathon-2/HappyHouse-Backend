package com.example.happyhousebackend.domain.routine.entity;

import lombok.*;
import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "routine_repeat")
public class RoutineRepeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "routine_repeat_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "routine_id")
    private Routine routine;

    private int day;

    @Builder.Default
    private boolean flag = true;

}
