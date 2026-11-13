package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SleepTrackerAppTest {

    @Test
    void totalSessions_empty() {
        String result = new TotalSessionsAnalyzer().apply(List.of()).getValue();
        assertEquals("0", result);
    }

    @Test
    void totalSessions_three() {
        List<SleepSession> list = List.of(
                new SleepSession(LocalDateTime.of(2024,1,1,23,0),
                        LocalDateTime.of(2024,1,2,7,0), Quality.GOOD),
                new SleepSession(LocalDateTime.of(2024,1,2,23,0),
                        LocalDateTime.of(2024,1,3,7,0), Quality.GOOD),
                new SleepSession(LocalDateTime.of(2024,1,3,23,0),
                        LocalDateTime.of(2024,1,4,7,0), Quality.GOOD)
        );
        String result = new TotalSessionsAnalyzer().apply(list).getValue();
        assertEquals("3", result);
    }

    @Test
    void insomniac_nights_empty() {
        assertEquals("0", new InsomniacNightsCountAnalyzer().apply(List.of()).getValue());
    }

    @Test
    void insomniac_nights_all_sleeping() {
        List<SleepSession> list = List.of(
                new SleepSession(LocalDateTime.of(2024,1,1,23,0),
                        LocalDateTime.of(2024,1,2,7,0), Quality.GOOD),
                new SleepSession(LocalDateTime.of(2024,1,2,23,0),
                        LocalDateTime.of(2024,1,3,7,0), Quality.GOOD)
        );
        assertEquals("0", new InsomniacNightsCountAnalyzer().apply(list).getValue());
    }

    @Test
    void insomniac_nights_all_day_sleep() {
        List<SleepSession> list = List.of(
                new SleepSession(
                        LocalDateTime.of(2024, 1, 1, 14, 0),
                        LocalDateTime.of(2024, 1, 1, 16, 0),
                        Quality.GOOD
                ),
                new SleepSession(
                        LocalDateTime.of(2024, 1, 2, 14, 0),
                        LocalDateTime.of(2024, 1, 2, 16, 0),
                        Quality.GOOD
                )
        );
        assertEquals("1", new InsomniacNightsCountAnalyzer().apply(list).getValue());
    }

    @Test
    void insomniac_nights_mixed() {
        List<SleepSession> list = List.of(
                new SleepSession(LocalDateTime.of(2024,1,1,23,0),
                        LocalDateTime.of(2024,1,2,7,0), Quality.GOOD),
                new SleepSession(LocalDateTime.of(2024,1,3,14,0),
                        LocalDateTime.of(2024,1,3,16,0), Quality.GOOD)
        );
        assertEquals("1", new InsomniacNightsCountAnalyzer().apply(list).getValue());
    }

    @Test
    void chronotype_owl() {
        List<SleepSession> list = List.of(
                new SleepSession(
                        LocalDateTime.of(2024, 1, 1, 23, 30),
                        LocalDateTime.of(2024, 1, 2, 10, 0),
                        Quality.GOOD
                )
        );
        assertEquals("Сова", new ChronotypeAnalyzer().apply(list).getValue());
    }

    @Test
    void chronotype_lark() {
        List<SleepSession> list = List.of(
                new SleepSession(
                        LocalDateTime.of(2024, 1, 1, 21, 30),
                        LocalDateTime.of(2024, 1, 2, 6, 30),
                        Quality.GOOD
                )
        );
        assertEquals("Жаворонок", new ChronotypeAnalyzer().apply(list).getValue());
    }

    @Test
    void minDuration_empty() {
        assertEquals("0", new MinDurationAnalyzer().apply(List.of()).getValue());
    }

    @Test
    void minDuration_two() {
        List<SleepSession> list = List.of(
                new SleepSession(LocalDateTime.of(2024,1,1,22,0), LocalDateTime.of(2024,1,2,6,0), Quality.GOOD),
                new SleepSession(LocalDateTime.of(2024,1,2,22,0), LocalDateTime.of(2024,1,3,4,0), Quality.GOOD)
        );
        assertEquals("360", new MinDurationAnalyzer().apply(list).getValue());
    }

    @Test
    void maxDuration_empty() {
        assertEquals("0", new MaxDurationAnalyzer().apply(List.of()).getValue());
    }

    @Test
    void maxDuration_two() {
        List<SleepSession> list = List.of(
                new SleepSession(LocalDateTime.of(2024,1,1,22,0), LocalDateTime.of(2024,1,2,6,0), Quality.GOOD),
                new SleepSession(LocalDateTime.of(2024,1,2,22,0), LocalDateTime.of(2024,1,3,4,0), Quality.GOOD)
        );
        assertEquals("480", new MaxDurationAnalyzer().apply(list).getValue());
    }

    @Test
    void avgDuration_empty() {
        assertEquals("0", new AvgDurationAnalyzer().apply(List.of()).getValue());
    }

    @Test
    void avgDuration_two() {
        List<SleepSession> list = List.of(
                new SleepSession(LocalDateTime.of(2024,1,1,22,0), LocalDateTime.of(2024,1,2,6,0), Quality.GOOD),
                new SleepSession(LocalDateTime.of(2024,1,2,22,0), LocalDateTime.of(2024,1,3,4,0), Quality.GOOD)
        );
        assertEquals("420", new AvgDurationAnalyzer().apply(list).getValue());
    }

    @Test
    void badQuality_empty() {
        assertEquals("0", new BadQualityCountAnalyzer().apply(List.of()).getValue());
    }

    @Test
    void badQuality_mixed() {
        List<SleepSession> list = List.of(
                new SleepSession(LocalDateTime.of(2024,1,1,22,0), LocalDateTime.of(2024,1,2,6,0), Quality.GOOD),
                new SleepSession(LocalDateTime.of(2024,1,2,22,0), LocalDateTime.of(2024,1,3,4,0), Quality.BAD)
        );
        assertEquals("1", new BadQualityCountAnalyzer().apply(list).getValue());
    }
}
