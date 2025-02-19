package com.algobench.pro.visualization.progress;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * ASCII-based progress bar for displaying operation progress.
 */
public class ProgressBar {
    private static final Logger logger = LogManager.getLogger(ProgressBar.class);
    private static final String TEMPLATE = "\r[%-50s] %3d%% %s";
    private static final char PROGRESS_CHAR = '█';
    private static final char EMPTY_CHAR = '░';

    private final int total;
    private int current;
    private String message;
    private long startTime;

    /**
     * Creates a new progress bar with the given total steps.
     * @param total Total number of steps
     */
    public ProgressBar(int total) {
        this(total, "");
    }

    /**
     * Creates a new progress bar with the given total steps and message.
     * @param total Total number of steps
     * @param message Message to display alongside the progress
     */
    public ProgressBar(int total, String message) {
        if (total <= 0) {
            throw new IllegalArgumentException("Total must be positive");
        }
        this.total = total;
        this.current = 0;
        this.message = message;
        this.startTime = System.currentTimeMillis();
        logger.debug("Created progress bar with {} steps", total);
    }

    /**
     * Updates the progress to the specified value.
     * @param value Current progress value
     */
    public void update(int value) {
        if (value < 0 || value > total) {
            logger.warn("Invalid progress value: {}", value);
            return;
        }
        this.current = value;
        render();
    }

    /**
     * Increments the progress by one step.
     */
    public void increment() {
        update(current + 1);
    }

    /**
     * Updates the message displayed alongside the progress bar.
     * @param message New message to display
     */
    public void setMessage(String message) {
        this.message = message != null ? message : "";
        render();
    }

    /**
     * Marks the progress as complete.
     */
    public void complete() {
        update(total);
        System.out.println(); // Move to next line
        long duration = System.currentTimeMillis() - startTime;
        logger.info("Progress completed in {} ms", duration);
    }

    /**
     * Gets the current progress percentage.
     * @return Progress percentage (0-100)
     */
    public int getPercentage() {
        return (int) ((current * 100.0) / total);
    }

    private void render() {
        int percent = getPercentage();
        int progressChars = (current * 50) / total;
        
        StringBuilder bar = new StringBuilder();
        for (int i = 0; i < 50; i++) {
            bar.append(i < progressChars ? PROGRESS_CHAR : EMPTY_CHAR);
        }

        System.out.printf(TEMPLATE, bar.toString(), percent, message);
        System.out.flush();
        
        logger.debug("Progress updated: {}%", percent);
    }

    /**
     * Creates a nested progress bar with a subset of steps.
     * @param steps Number of steps for the nested bar
     * @return A new progress bar representing a subset of the total progress
     */
    public ProgressBar createNested(int steps) {
        if (steps <= 0) {
            throw new IllegalArgumentException("Steps must be positive");
        }
        double stepsPerUnit = (double) steps / total;
        return new NestedProgressBar(this, steps, stepsPerUnit);
    }

    /**
     * Nested progress bar that updates the parent progress bar.
     */
    private static class NestedProgressBar extends ProgressBar {
        private final ProgressBar parent;
        private final double stepsPerUnit;
        private final int parentStart;

        private NestedProgressBar(ProgressBar parent, int total, double stepsPerUnit) {
            super(total);
            this.parent = parent;
            this.stepsPerUnit = stepsPerUnit;
            this.parentStart = parent.current;
        }

        @Override
        public void update(int value) {
            super.update(value);
            int parentProgress = parentStart + (int) (value / stepsPerUnit);
            parent.update(Math.min(parentProgress, parent.total));
        }
    }
}
