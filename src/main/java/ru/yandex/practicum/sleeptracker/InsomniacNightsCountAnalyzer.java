package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.util.List;
import java.time.temporal.ChronoUnit;

public class InsomniacNightsCountAnalyzer implements SleepAnalyzer {

    @Override
    public SleepAnalysisResult apply(List<SleepSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult("Бессонных ночей", "0");
        }

        LocalDate startDate = sessions.get(0).getSleepStart().toLocalDate();
        if (sessions.get(0).getSleepStart().getHour() < 12) {
            startDate = startDate.minusDays(1);
        }

        LocalDate endDate = sessions.get(sessions.size() - 1).getSleepEnd().toLocalDate();

        long totalNights = ChronoUnit.DAYS.between(startDate, endDate);

        long sleepingNights = sessions.stream()
                .filter(this::isNightSleep)
                .count();

        long result = Math.max(0, totalNights - sleepingNights);
        return new SleepAnalysisResult("Бессонных ночей", String.valueOf(result));
    }

    private boolean isNightSleep(SleepSession session) {
        var start = session.getSleepStart();
        var end = session.getSleepEnd();

        if (!start.toLocalDate().equals(end.toLocalDate())) {
            return true;
        }

        return start.getHour() < 6;
    }
}
