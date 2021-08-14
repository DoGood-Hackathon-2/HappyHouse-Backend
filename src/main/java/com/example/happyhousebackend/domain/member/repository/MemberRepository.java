package com.example.happyhousebackend.domain.member.repository;

import com.example.happyhousebackend.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    public List<Member> findAllByFamilyId(Long familyId);

}
