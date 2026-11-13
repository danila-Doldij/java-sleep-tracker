package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;

public class AvgDurationAnalyzer implements SleepAnalyzer {
    @Override
    public SleepAnalysisResult apply(List<SleepSession> sessions) {
        long avg = (long) sessions.stream()
                .mapToLong(s -> Duration.between(s.getSleepStart(), s.getSleepEnd()).toMinutes())
                .average().orElse(0);
        return new SleepAnalysisResult("Сред. длительность (мин)", String.valueOf(avg));
    }
}
