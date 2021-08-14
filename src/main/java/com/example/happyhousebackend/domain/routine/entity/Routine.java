package com.example.happyhousebackend.domain.routine.entity;

import com.example.happyhousebackend.domain.member.entity.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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

    @Builder.Default
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    @Builder.Default
    @Column(name = "updated_date")
    private LocalDateTime updatedDate = LocalDateTime.now();

}
