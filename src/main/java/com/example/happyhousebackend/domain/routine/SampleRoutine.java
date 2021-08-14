package com.example.happyhousebackend.domain.routine;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "sample_routine")
public class SampleRoutine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sample_routine_id")
    private Long id;

    private String title;

    private String subTitle;

}
