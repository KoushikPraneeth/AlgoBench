package com.algobench.pro.benchmark.results;

import com.algobench.pro.visualization.chart.ASCIIChartGenerator;
import com.algobench.pro.visualization.chart.Point2D;
import com.algobench.pro.visualization.table.TableFormatter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Generates formatted reports from benchmark results.
 */
public class PerformanceReport {
    private static final Logger logger = LogManager.getLogger(PerformanceReport.class);
    private final BenchmarkResult results;
    private final String timestamp;

    public PerformanceReport(BenchmarkResult results) {
        this.results = results;
        this.timestamp = LocalDateTime.now().format(
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        );
        logger.debug("Created performance report for {}", results.getAlgorithmName());
    }

    /**
     * Generates a detailed report in plain text format.
     * @return Formatted report string
     */
    public String generateTextReport() {
        StringBuilder report = new StringBuilder();
        report.append(String.format("Performance Report - %s%n", results.getAlgorithmName()))
              .append(String.format("Generated: %s%n%n", timestamp));

        // Create and populate table
        TableFormatter table = new TableFormatter(
            "Input Size", "Avg Time (ms)", "Min Time (ms)", 
            "Max Time (ms)", "Memory (MB)"
        );

        for (int size : results.getInputSizes()) {
            RunStatistics stats = results.getStatistics(size);
            if (stats != null) {
                table.addRow(
                    String.valueOf(size),
                    String.format("%.2f", stats.getAverage().toMillis()),
                    String.format("%.2f", stats.getMin().toMillis()),
                    String.format("%.2f", stats.getMax().toMillis()),
                    String.format("%.2f", results.getMemoryUsage().get(size) / 1024.0 / 1024.0)
                );
            }
        }

        report.append(table.format()).append("\n\n");

        // Add time complexity visualization
        report.append("Time Complexity Analysis:\n")
              .append(generateTimeComplexityChart())
              .append("\n\n");

        // Add memory usage visualization
        report.append("Memory Usage Analysis:\n")
              .append(generateMemoryUsageChart())
              .append("\n");

        logger.info("Generated text report for {}", results.getAlgorithmName());
        return report.toString();
    }

    /**
     * Generates a report in CSV format for data analysis.
     * @return CSV formatted string
     */
    public String generateCSV() {
        StringBuilder csv = new StringBuilder();
        csv.append("Input Size,Average Time (ms),Min Time (ms),Max Time (ms),")
           .append("Std Dev (ms),Memory Usage (MB)\n");

        for (int size : results.getInputSizes()) {
            RunStatistics stats = results.getStatistics(size);
            if (stats != null) {
                double memoryMB = results.getMemoryUsage().get(size) / 1024.0 / 1024.0;
                csv.append(String.format("%d,%.2f,%.2f,%.2f,%.2f,%.2f%n",
                    size,
                    stats.getAverage().toMillis(),
                    stats.getMin().toMillis(),
                    stats.getMax().toMillis(),
                    stats.getStandardDeviation(),
                    memoryMB
                ));
            }
        }

        logger.info("Generated CSV report for {}", results.getAlgorithmName());
        return csv.toString();
    }

    private String generateTimeComplexityChart() {
        List<Point2D> points = new ArrayList<>();
        for (int size : results.getInputSizes()) {
            RunStatistics stats = results.getStatistics(size);
            if (stats != null) {
                points.add(new Point2D(size, stats.getAverage().toMillis()));
            }
        }

        ASCIIChartGenerator chartGen = new ASCIIChartGenerator();
        return chartGen.generateLineChart(points);
    }

    private String generateMemoryUsageChart() {
        Map<String, Double> memoryData = new TreeMap<>();
        for (int size : results.getInputSizes()) {
            double memoryMB = results.getMemoryUsage().get(size) / 1024.0 / 1024.0;
            memoryData.put(String.valueOf(size), memoryMB);
        }

        ASCIIChartGenerator chartGen = new ASCIIChartGenerator();
        return chartGen.generateBarChart(memoryData);
    }

    /**
     * Generates a Markdown formatted report.
     * @return Markdown string
     */
    public String generateMarkdown() {
        StringBuilder md = new StringBuilder();
        md.append("# Performance Report - ").append(results.getAlgorithmName()).append("\n\n");
        md.append("Generated: ").append(timestamp).append("\n\n");

        // Performance metrics table
        md.append("## Performance Metrics\n\n");
        md.append("| Input Size | Avg Time (ms) | Min Time (ms) | Max Time (ms) | Memory (MB) |\n");
        md.append("|------------|---------------|---------------|---------------|-------------|\n");

        for (int size : results.getInputSizes()) {
            RunStatistics stats = results.getStatistics(size);
            if (stats != null) {
                md.append(String.format("| %d | %.2f | %.2f | %.2f | %.2f |\n",
                    size,
                    stats.getAverage().toMillis(),
                    stats.getMin().toMillis(),
                    stats.getMax().toMillis(),
                    results.getMemoryUsage().get(size) / 1024.0 / 1024.0
                ));
            }
        }

        // Add charts as ASCII art in code blocks
        md.append("\n## Time Complexity Analysis\n\n");
        md.append("```\n").append(generateTimeComplexityChart()).append("\n```\n\n");

        md.append("## Memory Usage Analysis\n\n");
        md.append("```\n").append(generateMemoryUsageChart()).append("\n```\n");

        logger.info("Generated Markdown report for {}", results.getAlgorithmName());
        return md.toString();
    }
}
