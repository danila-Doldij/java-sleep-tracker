package ru.yandex.practicum.sleeptracker;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class SleepTrackerApp {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    private static final List<SleepAnalyzer> ANALYZERS = List.of(
            new TotalSessionsAnalyzer(),
            new MinDurationAnalyzer(),
            new MaxDurationAnalyzer(),
            new AvgDurationAnalyzer(),
            new BadQualityCountAnalyzer(),
            new InsomniacNightsCountAnalyzer(),
            new ChronotypeAnalyzer()
    );

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Путь к файлу не указан");
            return;
        }

        try {
            List<SleepSession> sessions = Files.readAllLines(Path.of(args[0]))
                    .stream()
                    .filter(line -> !line.trim().isEmpty())
                    .map(line -> parseLine(line))
                    .collect(Collectors.toList());

            ANALYZERS.stream()
                    .map(analyzer -> analyzer.apply(sessions))
                    .forEach(result -> System.out.println(result.getDescription() + ": " +
                             result.getValue()));

        } catch (Exception e) {
            System.err.println("Ошибка чтения: " + e.getMessage());
        }
    }

    private static SleepSession parseLine(String line) {
        String[] parts = line.split(";");
        LocalDateTime start = LocalDateTime.parse(parts[0].trim(), FORMATTER);
        LocalDateTime end = LocalDateTime.parse(parts[1].trim(), FORMATTER);
        Quality quality = Quality.valueOf(parts[2].trim());
        return new SleepSession(start, end, quality);
    }
}
