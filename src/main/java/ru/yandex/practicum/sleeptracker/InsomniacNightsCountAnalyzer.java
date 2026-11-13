package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class InsomniacNightsCountAnalyzer implements SleepAnalyzer {

    @Override
    public SleepAnalysisResult apply(List<SleepSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult("Бессонных ночей", "0");
        }

        LocalDate firstStartDate = sessions.get(0).getSleepStart().toLocalDate();
        if (sessions.get(0).getSleepStart().getHour() < 12) {
            firstStartDate = firstStartDate.minusDays(1);
        }

        LocalDate lastEndDate = sessions.get(sessions.size() - 1).getSleepEnd().toLocalDate();

        long totalNights = ChronoUnit.DAYS.between(firstStartDate, lastEndDate);

        Set<LocalDate> nightsWithSleep = sessions.stream()
                .map(this::getCoveredNightDate)
                .filter(date -> date != null)
                .collect(Collectors.toSet());

        long sleepingNights = nightsWithSleep.size();
        long result = Math.max(0, totalNights - sleepingNights);

        return new SleepAnalysisResult("Бессонных ночей", String.valueOf(result));
    }

    private LocalDate getCoveredNightDate(SleepSession session) {
        LocalDateTime start = session.getSleepStart();
        LocalDateTime end = session.getSleepEnd();

        if (!start.toLocalDate().equals(end.toLocalDate())) {
            return start.toLocalDate();
        }

        if (start.getHour() < 6) {
            return start.toLocalDate();
        }

        return null;
    }
}