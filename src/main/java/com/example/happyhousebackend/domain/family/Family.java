package com.example.happyhousebackend.domain.family;

import com.example.happyhousebackend.domain.member.Member;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "family")
public class Family {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "family_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "family", cascade = CascadeType.ALL)
    private List<Member> memberList = new ArrayList<>();

}
