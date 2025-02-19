package com.algobench.pro.visualization.chart;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Generates ASCII-based charts for visualizing algorithm performance data.
 */
public class ASCIIChartGenerator {
    private static final Logger logger = LogManager.getLogger(ASCIIChartGenerator.class);
    private static final int CHART_WIDTH = 80;
    private static final int CHART_HEIGHT = 20;
    private static final char BAR_CHAR = '█';
    private static final char AXIS_CHAR = '│';
    private static final char BASELINE_CHAR = '─';

    /**
     * Generates a bar chart from the provided data.
     * @param data Map of labels to values
     * @return ASCII representation of the bar chart
     */
    public String generateBarChart(Map<String, Double> data) {
        if (data == null || data.isEmpty()) {
            logger.warn("Cannot generate bar chart: no data provided");
            return "No data available for visualization";
        }

        double maxValue = Collections.max(data.values());
        List<String> labels = new ArrayList<>(data.keySet());
        int maxLabelLength = getMaxLabelLength(labels);
        
        StringBuilder chart = new StringBuilder();
        chart.append(generateTitle("Bar Chart")).append("\n\n");

        // Generate bars
        for (String label : labels) {
            double value = data.get(label);
            int barLength = calculateBarLength(value, maxValue);
            String bar = generateBar(barLength);
            
            chart.append(String.format("%-" + maxLabelLength + "s %s %.2f%n", 
                                     label, bar, value));
        }

        logger.debug("Generated bar chart with {} entries", data.size());
        return chart.toString();
    }

    /**
     * Generates a line chart from the provided points.
     * @param points List of points (x, y coordinates)
     * @return ASCII representation of the line chart
     */
    public String generateLineChart(List<Point2D> points) {
        if (points == null || points.isEmpty()) {
            logger.warn("Cannot generate line chart: no points provided");
            return "No data available for visualization";
        }

        // Sort points by x coordinate
        points.sort(Comparator.comparingDouble(Point2D::getX));

        double minX = points.stream().mapToDouble(Point2D::getX).min().orElse(0);
        double maxX = points.stream().mapToDouble(Point2D::getX).max().orElse(0);
        double minY = points.stream().mapToDouble(Point2D::getY).min().orElse(0);
        double maxY = points.stream().mapToDouble(Point2D::getY).max().orElse(0);

        // Create chart grid
        char[][] grid = new char[CHART_HEIGHT][CHART_WIDTH];
        initializeGrid(grid);

        // Plot points
        for (int i = 0; i < points.size() - 1; i++) {
            Point2D current = points.get(i);
            Point2D next = points.get(i + 1);
            
            plotLine(grid, current, next, minX, maxX, minY, maxY);
        }

        // Convert grid to string
        StringBuilder chart = new StringBuilder();
        chart.append(generateTitle("Line Chart")).append("\n\n");
        
        // Add y-axis labels and grid content
        for (int i = 0; i < CHART_HEIGHT; i++) {
            double yValue = maxY - (i * (maxY - minY) / (CHART_HEIGHT - 1));
            chart.append(String.format("%8.2f %s%n", yValue, new String(grid[i])));
        }

        // Add x-axis labels
        chart.append("         ");
        for (int i = 0; i < CHART_WIDTH; i += CHART_WIDTH / 4) {
            double xValue = minX + (i * (maxX - minX) / (CHART_WIDTH - 1));
            chart.append(String.format("%-20.2f", xValue));
        }

        logger.debug("Generated line chart with {} points", points.size());
        return chart.toString();
    }

    private void initializeGrid(char[][] grid) {
        for (int i = 0; i < CHART_HEIGHT; i++) {
            Arrays.fill(grid[i], ' ');
            grid[i][0] = AXIS_CHAR;
        }
        Arrays.fill(grid[CHART_HEIGHT - 1], BASELINE_CHAR);
        grid[CHART_HEIGHT - 1][0] = '+';
    }

    private void plotLine(char[][] grid, Point2D p1, Point2D p2, 
                         double minX, double maxX, double minY, double maxY) {
        int x1 = mapToGrid(p1.getX(), minX, maxX, 1, CHART_WIDTH - 1);
        int y1 = mapToGrid(p1.getY(), minY, maxY, CHART_HEIGHT - 2, 0);
        int x2 = mapToGrid(p2.getX(), minX, maxX, 1, CHART_WIDTH - 1);
        int y2 = mapToGrid(p2.getY(), minY, maxY, CHART_HEIGHT - 2, 0);

        // Bresenham's line algorithm
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;
        int err = dx - dy;

        while (true) {
            if (isInBounds(x1, y1, grid)) {
                grid[y1][x1] = '*';
            }

            if (x1 == x2 && y1 == y2) {
                break;
            }

            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }
        }
    }

    private String generateBar(int length) {
        return IntStream.range(0, length)
                       .mapToObj(i -> String.valueOf(BAR_CHAR))
                       .collect(Collectors.joining());
    }

    private int calculateBarLength(double value, double maxValue) {
        return (int) Math.round((value / maxValue) * (CHART_WIDTH - 20));
    }

    private String generateTitle(String title) {
        int padding = (CHART_WIDTH - title.length()) / 2;
        return " ".repeat(padding) + title;
    }

    private int getMaxLabelLength(List<String> labels) {
        return labels.stream()
                    .mapToInt(String::length)
                    .max()
                    .orElse(10);
    }

    private int mapToGrid(double value, double min, double max, int gridMin, int gridMax) {
        return (int) Math.round(gridMin + (gridMax - gridMin) * (value - min) / (max - min));
    }

    private boolean isInBounds(int x, int y, char[][] grid) {
        return y >= 0 && y < grid.length && x >= 0 && x < grid[0].length;
    }
}
