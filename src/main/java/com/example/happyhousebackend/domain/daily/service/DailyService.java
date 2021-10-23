package com.example.happyhousebackend.domain.daily.service;

import com.example.happyhousebackend.domain.daily.entity.Daily;
import com.example.happyhousebackend.domain.daily.repository.DailyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DailyService {

    private final DailyRepository dailyRepository;

    public void saveDaily(Daily daily) {
        dailyRepository.save(daily);
    }

}
