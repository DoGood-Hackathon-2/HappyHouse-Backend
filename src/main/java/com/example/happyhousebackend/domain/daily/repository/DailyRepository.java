package com.example.happyhousebackend.domain.daily.repository;

import com.example.happyhousebackend.domain.daily.entity.Daily;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyRepository extends JpaRepository<Daily, Long> {
}
