package com.algobench.pro.benchmark;

import com.algobench.pro.util.config.ConfigurationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

/**
 * Configuration settings for benchmarking operations.
 */
public class BenchmarkConfig {
    private static final Logger logger = LogManager.getLogger(BenchmarkConfig.class);
    private static final ConfigurationManager config = ConfigurationManager.getInstance();

    private final int warmupRuns;
    private final int measurementRuns;
    private final int[] inputSizes;
    private final boolean gcBeforeBenchmark;
    private final long timeoutSeconds;

    /**
     * Creates a benchmark configuration with default settings from application.properties.
     */
    public BenchmarkConfig() {
        this.warmupRuns = config.getIntProperty("benchmark.warmup.runs", 3);
        this.measurementRuns = config.getIntProperty("benchmark.measurement.runs", 5);
        this.gcBeforeBenchmark = config.getBooleanProperty("performance.gc.before.benchmark", true);
        this.timeoutSeconds = config.getIntProperty("performance.timeout.seconds", 30);
        
        String sizesList = config.getProperty("benchmark.default.array.sizes", "1000,5000,10000,50000");
        this.inputSizes = Arrays.stream(sizesList.split(","))
                               .mapToInt(Integer::parseInt)
                               .toArray();
        
        logConfiguration();
    }

    /**
     * Creates a benchmark configuration with custom settings.
     */
    public BenchmarkConfig(int warmupRuns, int measurementRuns, int[] inputSizes, 
                          boolean gcBeforeBenchmark, long timeoutSeconds) {
        this.warmupRuns = warmupRuns;
        this.measurementRuns = measurementRuns;
        this.inputSizes = inputSizes.clone();
        this.gcBeforeBenchmark = gcBeforeBenchmark;
        this.timeoutSeconds = timeoutSeconds;
        
        logConfiguration();
    }

    private void logConfiguration() {
        logger.info("Benchmark configuration initialized:");
        logger.info("Warmup runs: {}", warmupRuns);
        logger.info("Measurement runs: {}", measurementRuns);
        logger.info("Input sizes: {}", Arrays.toString(inputSizes));
        logger.info("GC before benchmark: {}", gcBeforeBenchmark);
        logger.info("Timeout: {} seconds", timeoutSeconds);
    }

    public int getWarmupRuns() {
        return warmupRuns;
    }

    public int getMeasurementRuns() {
        return measurementRuns;
    }

    public int[] getInputSizes() {
        return inputSizes.clone();
    }

    public boolean isGcBeforeBenchmark() {
        return gcBeforeBenchmark;
    }

    public long getTimeoutSeconds() {
        return timeoutSeconds;
    }

    @Override
    public String toString() {
        return String.format(
            "BenchmarkConfig{warmupRuns=%d, measurementRuns=%d, inputSizes=%s, " +
            "gcBeforeBenchmark=%b, timeoutSeconds=%d}",
            warmupRuns, measurementRuns, Arrays.toString(inputSizes),
            gcBeforeBenchmark, timeoutSeconds
        );
    }
}
