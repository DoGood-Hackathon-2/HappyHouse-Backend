package com.example.happyhousebackend.domain.member.entity;

import com.example.happyhousebackend.domain.family.entity.Family;
import com.example.happyhousebackend.domain.member.dto.MemberDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(nullable = false)
    private String email;

    private String intro;

    private String image;

    @Column(nullable = false)
    private String socialType;

    private String socialId;

    private String refreshToken;

    @ManyToOne
    @JoinColumn(name = "family_id")
    private Family family;

    public void setFamily(Family family) {
        this.family = family;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void changeImage(String image) {
        this.image = image;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Member update(String nickname, String image) {
        this.nickname = nickname;
        this.image = image;

        return this;
    }

    public static MemberDto entityToDto(Member entity) {
        return MemberDto.builder()
                .id(entity.getId())
                .nickname(entity.getNickname())
                .image(entity.getImage())
                .build();
    }

}
