package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;

public class MinDurationAnalyzer implements SleepAnalyzer {
    @Override
    public SleepAnalysisResult apply(List<SleepSession> sessions) {
        long min = sessions.stream()
                .mapToLong(s -> Duration.between(s.getSleepStart(), s.getSleepEnd()).toMinutes())
                .min().orElse(0);
        return new SleepAnalysisResult("Мин. длительность (мин)", String.valueOf(min));
    }
}
