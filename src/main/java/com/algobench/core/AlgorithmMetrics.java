package com.algobench.core;

/**
 * Tracks and stores performance metrics for algorithm executions.
 * This includes operation counts, timing, and memory usage.
 */
public class AlgorithmMetrics {
    private long comparisons;
    private long swaps;
    private long startTime;
    private long endTime;
    private long memoryUsed;

    public AlgorithmMetrics() {
        reset();
    }

    public void startTimer() {
        startTime = System.nanoTime();
    }

    public void stopTimer() {
        endTime = System.nanoTime();
    }

    public void incrementComparisons() {
        comparisons++;
    }

    public void incrementSwaps() {
        swaps++;
    }

    public void updateMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        memoryUsed = runtime.totalMemory() - runtime.freeMemory();
    }

    public void reset() {
        comparisons = 0;
        swaps = 0;
        startTime = 0;
        endTime = 0;
        memoryUsed = 0;
    }

    public long getComparisons() {
        return comparisons;
    }

    public long getSwaps() {
        return swaps;
    }

    public long getExecutionTimeNanos() {
        return endTime - startTime;
    }

    public long getMemoryUsed() {
        return memoryUsed;
    }

    @Override
    public String toString() {
        return String.format(
            "Comparisons: %d | Swaps: %d | Time: %.2fms | Memory: %dKB",
            comparisons,
            swaps,
            getExecutionTimeNanos() / 1_000_000.0,
            memoryUsed / 1024
        );
    }
}