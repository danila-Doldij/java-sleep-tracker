package ru.yandex.practicum.sleeptracker;

import java.util.function.Function;
import java.util.List;

public interface SleepAnalyzer extends Function<List<SleepSession>, SleepAnalysisResult> {
}
