package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class TotalSessionsAnalyzer implements SleepAnalyzer {
    @Override
    public SleepAnalysisResult apply(List<SleepSession> sessions) {
        return new SleepAnalysisResult("Всего сессий сна", String.valueOf(sessions.size()));
    }
}
