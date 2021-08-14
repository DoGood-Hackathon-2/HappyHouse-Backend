package com.example.happyhousebackend.domain.member.entity;

import com.example.happyhousebackend.domain.family.entity.Family;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String nickname;

    private String email;

    private String intro;

    private String image;

    private int socialType;

    private String socialId;

    @ManyToOne
    @JoinColumn(name = "family_id")
    private Family family;

}
