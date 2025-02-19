package com.algobench.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AlgorithmMetricsTest {
    private AlgorithmMetrics metrics;

    @BeforeEach
    void setUp() {
        metrics = new AlgorithmMetrics();
    }

    @Test
    void testInitialState() {
        assertEquals(0, metrics.getComparisons());
        assertEquals(0, metrics.getSwaps());
        assertEquals(0, metrics.getExecutionTimeNanos());
        assertTrue(metrics.getMemoryUsed() >= 0);
    }

    @Test
    void testIncrementOperations() {
        metrics.incrementComparisons();
        metrics.incrementComparisons();
        metrics.incrementSwaps();

        assertEquals(2, metrics.getComparisons());
        assertEquals(1, metrics.getSwaps());
    }

    @Test
    void testTimer() throws InterruptedException {
        metrics.startTimer();
        Thread.sleep(100); // Sleep for 100ms
        metrics.stopTimer();

        assertTrue(metrics.getExecutionTimeNanos() >= 100_000_000); // 100ms in nanoseconds
    }

    @Test
    void testReset() {
        metrics.incrementComparisons();
        metrics.incrementSwaps();
        metrics.startTimer();
        metrics.stopTimer();
        metrics.updateMemoryUsage();

        metrics.reset();

        assertEquals(0, metrics.getComparisons());
        assertEquals(0, metrics.getSwaps());
        assertEquals(0, metrics.getExecutionTimeNanos());
    }

    @Test
    void testToString() {
        metrics.incrementComparisons();
        metrics.incrementSwaps();
        metrics.startTimer();
        metrics.stopTimer();
        metrics.updateMemoryUsage();

        String result = metrics.toString();
        assertTrue(result.contains("Comparisons: 1"));
        assertTrue(result.contains("Swaps: 1"));
        assertTrue(result.contains("Time:"));
        assertTrue(result.contains("Memory:"));
    }
}