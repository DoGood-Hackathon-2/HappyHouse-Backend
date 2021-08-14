package com.example.happyhousebackend.domain.family.repository;

import com.example.happyhousebackend.domain.family.entity.Family;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamilyRepository extends JpaRepository<Family, Long> {
}
