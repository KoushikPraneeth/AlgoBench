package com.algobench.pro.benchmark.results;

import java.time.Duration;
import java.util.Collection;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Statistical analysis of benchmark run timings.
 */
public class RunStatistics {
    private final Duration min;
    private final Duration max;
    private final Duration average;
    private final double standardDeviation;
    private final List<Duration> timings;

    public RunStatistics(Collection<Duration> timings) {
        this.timings = List.copyOf(timings);

        // Convert durations to milliseconds for calculations
        DoubleSummaryStatistics stats = timings.stream()
            .mapToDouble(d -> d.toNanos() / 1_000_000.0)
            .summaryStatistics();

        // Calculate standard deviation
        double mean = stats.getAverage();
        this.standardDeviation = Math.sqrt(
            timings.stream()
                .mapToDouble(d -> d.toNanos() / 1_000_000.0)
                .map(t -> Math.pow(t - mean, 2))
                .average()
                .orElse(0.0)
        );

        // Store results as Duration objects
        this.min = Duration.ofNanos((long)(stats.getMin() * 1_000_000));
        this.max = Duration.ofNanos((long)(stats.getMax() * 1_000_000));
        this.average = Duration.ofNanos((long)(mean * 1_000_000));
    }

    public Duration getMin() {
        return min;
    }

    public Duration getMax() {
        return max;
    }

    public Duration getAverage() {
        return average;
    }

    public double getStandardDeviation() {
        return standardDeviation;
    }

    public List<Duration> getTimings() {
        return timings;
    }

    @Override
    public String toString() {
        return String.format(
            "RunStatistics{min=%.2fms, max=%.2fms, avg=%.2fms, stddev=%.2fms}",
            min.toNanos() / 1_000_000.0,
            max.toNanos() / 1_000_000.0,
            average.toNanos() / 1_000_000.0,
            standardDeviation
        );
    }

    /**
     * Gets the timings formatted as a comma-separated string of millisecond values.
     */
    public String getTimingsCSV() {
        return timings.stream()
            .map(d -> String.format("%.2f", d.toNanos() / 1_000_000.0))
            .collect(Collectors.joining(","));
    }
}
