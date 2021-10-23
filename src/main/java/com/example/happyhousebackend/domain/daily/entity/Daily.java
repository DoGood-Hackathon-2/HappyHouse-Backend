package com.example.happyhousebackend.domain.daily.entity;

import com.example.happyhousebackend.domain.member.entity.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "daily")
public class Daily {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "daily_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "writer_id")
    private Member member;

    @Column(nullable = false)
    private String content;

    @Column(name = "image_url")
    private String imageUrl;

    @Builder.Default
    @Column(name = "created_date", updatable = false, nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

}
