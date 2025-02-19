package com.algobench.pro.benchmark.results;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.util.*;

/**
 * Stores and analyzes benchmark results.
 */
public class BenchmarkResult {
    private static final Logger logger = LogManager.getLogger(BenchmarkResult.class);

    private final String algorithmName;
    private final Map<Integer, List<Duration>> sizeToTimings;
    private final Map<Integer, Long> sizeToMemoryUsage;
    private final int warmupRuns;
    private final int measurementRuns;

    public BenchmarkResult(String algorithmName, int warmupRuns, int measurementRuns) {
        this.algorithmName = algorithmName;
        this.warmupRuns = warmupRuns;
        this.measurementRuns = measurementRuns;
        this.sizeToTimings = new TreeMap<>();
        this.sizeToMemoryUsage = new TreeMap<>();
    }

    /**
     * Adds timing result for a specific input size.
     */
    public void addTiming(int inputSize, Duration duration) {
        sizeToTimings.computeIfAbsent(inputSize, k -> new ArrayList<>()).add(duration);
        logger.debug("Added timing for size {}: {}", inputSize, duration);
    }

    /**
     * Records memory usage for a specific input size.
     */
    public void setMemoryUsage(int inputSize, long memoryBytes) {
        sizeToMemoryUsage.put(inputSize, memoryBytes);
        logger.debug("Recorded memory usage for size {}: {} bytes", inputSize, memoryBytes);
    }

    /**
     * Gets statistics for a specific input size.
     */
    public RunStatistics getStatistics(int inputSize) {
        List<Duration> timings = sizeToTimings.get(inputSize);
        if (timings == null || timings.isEmpty()) {
            logger.warn("No timings available for input size: {}", inputSize);
            return null;
        }

        // Skip warmup runs and calculate statistics
        List<Duration> measurementTimings = timings.subList(
            Math.min(warmupRuns, timings.size()),
            timings.size()
        );

        return new RunStatistics(measurementTimings);
    }

    /**
     * Generates a summary report of the benchmark results.
     */
    public String generateReport() {
        StringBuilder report = new StringBuilder();
        report.append(String.format("Benchmark Results for %s%n", algorithmName));
        report.append(String.format("Configuration: %d warmup runs, %d measurement runs%n",
                                  warmupRuns, measurementRuns));
        report.append(String.format("%n%-10s %-15s %-15s %-15s %-15s%n",
                                  "Size", "Avg Time (ms)", "Min Time (ms)", "Max Time (ms)", "Memory (MB)"));
        report.append("-".repeat(70)).append("\n");

        for (int size : sizeToTimings.keySet()) {
            RunStatistics stats = getStatistics(size);
            if (stats != null) {
                double avgMs = stats.getAverage().toMillis();
                double minMs = stats.getMin().toMillis();
                double maxMs = stats.getMax().toMillis();
                double memoryMB = sizeToMemoryUsage.getOrDefault(size, 0L) / 1024.0 / 1024.0;

                report.append(String.format("%-10d %-15.2f %-15.2f %-15.2f %-15.2f%n",
                                         size, avgMs, minMs, maxMs, memoryMB));
            }
        }

        return report.toString();
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public Set<Integer> getInputSizes() {
        return Collections.unmodifiableSet(sizeToTimings.keySet());
    }

    public Map<Integer, Long> getMemoryUsage() {
        return Collections.unmodifiableMap(sizeToMemoryUsage);
    }
}
