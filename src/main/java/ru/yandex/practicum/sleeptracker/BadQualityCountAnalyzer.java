package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class BadQualityCountAnalyzer implements SleepAnalyzer {
    @Override
    public SleepAnalysisResult apply(List<SleepSession> sessions) {
        long count = sessions.stream()
                .filter(s -> s.getQuality() == Quality.BAD)
                .count();
        return new SleepAnalysisResult("Плохих сессий", String.valueOf(count));
    }
}
