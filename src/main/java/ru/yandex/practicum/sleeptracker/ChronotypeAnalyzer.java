package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChronotypeAnalyzer implements SleepAnalyzer {

    @Override
    public SleepAnalysisResult apply(List<SleepSession> sessions) {
        Map<LocalDate, SleepSession> firstSessionOfNight = sessions.stream()
                .filter(this::isNightSession)
                .collect(Collectors.toMap(
                        this::getCoveredNightDate,
                        s -> s,
                        (existing, replacement) -> existing
                ));

        long countOwls = firstSessionOfNight.values().stream()
                .filter(s -> s.getSleepStart().toLocalTime().isAfter(LocalTime.of(23, 0))
                        && s.getSleepEnd().toLocalTime().isAfter(LocalTime.of(9, 0)))
                .count();

        long countLarks = firstSessionOfNight.values().stream()
                .filter(s -> s.getSleepStart().toLocalTime().isBefore(LocalTime.of(22, 0))
                        && s.getSleepEnd().toLocalTime().isBefore(LocalTime.of(7, 0)))
                .count();

        long totalNights = firstSessionOfNight.size();
        long countPigeons = totalNights - countOwls - countLarks;

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

    private LocalDate getCoveredNightDate(SleepSession s) {
        return s.getSleepStart().toLocalDate();
    }
}
