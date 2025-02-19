package com.algobench.pro.benchmark.metrics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Utility class for tracking memory usage during algorithm execution.
 */
public class MemoryTracker {
    private static final Logger logger = LogManager.getLogger(MemoryTracker.class);
    private static final int GC_ATTEMPTS = 5;
    private static final long GC_WAIT_MS = 100;

    private final Runtime runtime;
    private long baselineMemory;

    public MemoryTracker() {
        this.runtime = Runtime.getRuntime();
        this.baselineMemory = 0;
    }

    /**
     * Takes a memory snapshot to use as baseline.
     * Useful for measuring memory used by a specific operation.
     */
    public void snapshot() {
        gcAndWait();
        baselineMemory = getUsedMemory();
        logger.debug("Memory baseline set to: {} bytes", baselineMemory);
    }

    /**
     * Gets the current memory usage in bytes.
     * @return Current memory usage in bytes
     */
    public long getUsedMemory() {
        return runtime.totalMemory() - runtime.freeMemory();
    }

    /**
     * Gets the memory usage difference from the last snapshot.
     * @return Memory usage difference in bytes
     */
    public long getMemoryDelta() {
        long currentMemory = getUsedMemory();
        long delta = currentMemory - baselineMemory;
        logger.debug("Memory delta: {} bytes (current: {}, baseline: {})", 
                    delta, currentMemory, baselineMemory);
        return delta;
    }

    /**
     * Gets the maximum available memory.
     * @return Maximum memory in bytes
     */
    public long getMaxMemory() {
        return runtime.maxMemory();
    }

    /**
     * Forces garbage collection and waits for it to complete.
     * Makes multiple attempts to ensure stability.
     */
    public void gcAndWait() {
        long prevMemory;
        long currentMemory = getUsedMemory();

        logger.debug("Starting garbage collection cycle");
        for (int i = 0; i < GC_ATTEMPTS; i++) {
            prevMemory = currentMemory;
            System.gc();
            try {
                Thread.sleep(GC_WAIT_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.warn("GC wait interrupted", e);
                break;
            }
            
            currentMemory = getUsedMemory();
            if (currentMemory >= prevMemory) {
                // Memory usage stabilized
                break;
            }
            
            logger.debug("GC cycle {}: {} bytes freed", 
                        i + 1, prevMemory - currentMemory);
        }
        logger.debug("Garbage collection completed. Final memory usage: {} bytes", 
                    currentMemory);
    }

    /**
     * Returns memory usage statistics as a formatted string.
     * @return Formatted memory statistics
     */
    public String getMemoryStats() {
        return String.format(
            "Memory Stats:%n" +
            "Current Usage: %.2f MB%n" +
            "Max Available: %.2f MB%n" +
            "Usage from Baseline: %.2f MB",
            getUsedMemory() / 1024.0 / 1024.0,
            getMaxMemory() / 1024.0 / 1024.0,
            getMemoryDelta() / 1024.0 / 1024.0
        );
    }
}
