package com.example.happyhousebackend.domain.routine.entity;

import com.example.happyhousebackend.domain.family.entity.Family;
import com.example.happyhousebackend.domain.member.entity.Member;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "routine")
public class Routine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "routine_id")
    private Long id;

    private String title;

    private String subTitle;

    private LocalDate startDate;

    private LocalTime time;

    @ManyToOne
    @JoinColumn(name = "requester_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "family_id")
    private Family family;

    @Builder.Default
    @Column(name = "created_date", updatable = false, nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    @Builder.Default
    @Column(name = "updated_date", nullable = false)
    private LocalDateTime updatedDate = LocalDateTime.now();

    @Builder.Default
    @OneToMany(mappedBy = "routine")
    private List<RoutineCompleted> routineCompletedList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "routine", cascade = CascadeType.ALL)
    private List<RoutineRepeat> routineRepeatList = new ArrayList<>();

    public void updateRoutine(Routine newRoutine) {
        this.title = newRoutine.getTitle();
        this.subTitle = newRoutine.getSubTitle();
        this.startDate = newRoutine.getStartDate();
        this.time = newRoutine.getTime();
    }

}
