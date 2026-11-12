package ru.yandex.practicum.sleeptracker;

import java.time.LocalTime;
import java.util.List;

public class ChronotypeAnalyzer implements SleepAnalyzer {

    @Override
    public SleepAnalysisResult apply(List<SleepSession> sessions) {

        long countOwls = sessions.stream()
                .filter(this::isNightSession)
                .filter(s -> s.getSleepStart().toLocalTime().isAfter(LocalTime.of(23, 0)) &&
                        s.getSleepEnd().toLocalTime().isAfter(LocalTime.of(9, 0)))
                .count();

        long countLarks = sessions.stream()
                .filter(this::isNightSession)
                .filter(s -> s.getSleepStart().toLocalTime().isBefore(LocalTime.of(22, 0)) &&
                        s.getSleepEnd().toLocalTime().isBefore(LocalTime.of(7, 0)))
                .count();

        long totalNightSessions = sessions.stream().filter(this::isNightSession).count();
        long countPigeons = totalNightSessions - countOwls - countLarks;

        String chronotype = "Голубь";
        if (countOwls > countLarks && countOwls > countPigeons) {
            chronotype = "Сова";
        } else if (countLarks > countOwls && countLarks > countPigeons) {
            chronotype = "Жаворонок";
        }

        return new SleepAnalysisResult("Хронотип", chronotype);
    }

    private boolean isNightSession(SleepSession s) {
        if (!s.getSleepStart().toLocalDate().equals(s.getSleepEnd().toLocalDate())) {
            return true;
        }
        return s.getSleepStart().toLocalTime().isBefore(LocalTime.of(6, 0));
    }
}
