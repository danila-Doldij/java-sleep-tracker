package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;

public class MaxDurationAnalyzer implements SleepAnalyzer {
    @Override
    public SleepAnalysisResult apply(List<SleepSession> sessions) {
        long max = sessions.stream()
                .mapToLong(s -> Duration.between(s.getSleepStart(), s.getSleepEnd()).toMinutes())
                .max().orElse(0);
        return new SleepAnalysisResult("Макс. длительность (мин)", String.valueOf(max));
    }
}
