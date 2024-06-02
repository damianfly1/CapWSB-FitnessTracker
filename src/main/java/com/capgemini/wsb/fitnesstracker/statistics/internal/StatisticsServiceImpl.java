package com.capgemini.wsb.fitnesstracker.statistics.internal;

import com.capgemini.wsb.fitnesstracker.statistics.api.Statistics;
import com.capgemini.wsb.fitnesstracker.statistics.api.StatisticsProvider;
import com.capgemini.wsb.fitnesstracker.statistics.api.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsProvider, StatisticsService {

    private final StatisticsRepository statisticsRepository;

    @Override
    public Optional<Statistics> getStatistics(final Long StatisticsId) {
        throw new UnsupportedOperationException("Not finished yet");
    }

    @Override
    public void deleteStatistics(Long id) {
        statisticsRepository.deleteById(id);
    }
}
