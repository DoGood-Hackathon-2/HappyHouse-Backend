package com.example.happyhousebackend.domain.member.repository;

import com.example.happyhousebackend.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findAllByFamilyId(Long familyId);

    Optional<Member> findByEmail(String email);

}
