package ru.yandex.practicum.sleeptracker;

import java.time.LocalDateTime;

public class SleepSession {
    private final LocalDateTime sleepStart;
    private final LocalDateTime sleepEnd;
    private final Quality quality;

    public SleepSession(LocalDateTime sleepStart, LocalDateTime sleepEnd, Quality quality) {
        this.sleepStart = sleepStart;
        this.sleepEnd = sleepEnd;
        this.quality = quality;
    }

    public LocalDateTime getSleepStart() {
        return sleepStart;
    }

    public LocalDateTime getSleepEnd() {
        return sleepEnd;
    }

    public Quality getQuality() {
        return quality;
    }
}
