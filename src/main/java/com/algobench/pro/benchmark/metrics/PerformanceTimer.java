package com.algobench.pro.benchmark.metrics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.Duration;

/**
 * Utility class for measuring execution time of algorithms.
 */
public class PerformanceTimer {
    private static final Logger logger = LogManager.getLogger(PerformanceTimer.class);
    private long startTime;
    private long endTime;
    private boolean isRunning;

    public PerformanceTimer() {
        this.isRunning = false;
    }

    /**
     * Starts the timer.
     * @throws IllegalStateException if the timer is already running
     */
    public void start() {
        if (isRunning) {
            logger.error("Timer is already running");
            throw new IllegalStateException("Timer is already running");
        }
        startTime = System.nanoTime();
        isRunning = true;
        logger.debug("Timer started");
    }

    /**
     * Stops the timer and returns the elapsed duration.
     * @return Duration since the timer was started
     * @throws IllegalStateException if the timer is not running
     */
    public Duration stop() {
        if (!isRunning) {
            logger.error("Timer is not running");
            throw new IllegalStateException("Timer is not running");
        }
        endTime = System.nanoTime();
        isRunning = false;
        Duration elapsed = Duration.ofNanos(endTime - startTime);
        logger.debug("Timer stopped. Elapsed time: {} ms", elapsed.toMillis());
        return elapsed;
    }

    /**
     * Returns whether the timer is currently running.
     * @return true if the timer is running, false otherwise
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Returns the elapsed time in nanoseconds.
     * @return elapsed time in nanoseconds
     * @throws IllegalStateException if the timer is still running
     */
    public long getElapsedNanos() {
        if (isRunning) {
            logger.error("Timer is still running");
            throw new IllegalStateException("Cannot get elapsed time while timer is running");
        }
        return endTime - startTime;
    }

    /**
     * Returns the elapsed time in milliseconds.
     * @return elapsed time in milliseconds
     * @throws IllegalStateException if the timer is still running
     */
    public double getElapsedMillis() {
        return getElapsedNanos() / 1_000_000.0;
    }
}
