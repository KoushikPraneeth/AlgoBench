package com.algobench.pro.ui.menu;

import com.algobench.pro.algorithm.searching.BinarySearch;
import com.algobench.pro.algorithm.searching.SearchAlgorithm;
import com.algobench.pro.algorithm.sorting.QuickSort;
import com.algobench.pro.algorithm.sorting.SortingAlgorithm;
import com.algobench.pro.benchmark.BenchmarkConfig;
import com.algobench.pro.benchmark.results.BenchmarkResult;
import com.algobench.pro.benchmark.results.PerformanceReport;
import com.algobench.pro.benchmark.runner.BenchmarkRunner;
import com.algobench.pro.ui.console.InputHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Menu handler for benchmark operations.
 */
public class BenchmarkMenu implements MenuItem {
    private static final Logger logger = LogManager.getLogger(BenchmarkMenu.class);
    private final InputHandler inputHandler;
    private final BenchmarkRunner benchmarkRunner;

    public BenchmarkMenu() {
        this.inputHandler = new InputHandler();
        this.benchmarkRunner = new BenchmarkRunner();
    }

    @Override
    public void execute() {
        logger.info("Entering benchmark menu");
        
        while (true) {
            System.out.println("\n=== Benchmark Menu ===");
            System.out.println("1. Benchmark Sorting Algorithms");
            System.out.println("2. Benchmark Searching Algorithms");
            System.out.println("0. Return to Main Menu");
            
            int choice = inputHandler.getIntInRange("\nEnter your choice: ", 0, 2);
            
            switch (choice) {
                case 0:
                    logger.info("Exiting benchmark menu");
                    return;
                case 1:
                    benchmarkSortingAlgorithms();
                    break;
                case 2:
                    benchmarkSearchingAlgorithms();
                    break;
            }
        }
    }

    @Override
    public String getDescription() {
        return "Run Benchmarks";
    }

    private void benchmarkSortingAlgorithms() {
        logger.info("Starting sorting algorithms benchmark");
        System.out.println("\nBenchmarking QuickSort...");

        try {
            SortingAlgorithm<Integer> quickSort = new QuickSort<>();
            BenchmarkResult result = benchmarkRunner.benchmarkSorting(quickSort);
            
                System.out.println("\nBenchmark Complete!");
                
                PerformanceReport report = new PerformanceReport(result);
                
                while (true) {
                    System.out.println("\n=== Report Format ===");
                    System.out.println("1. Text Report (Console)");
                    System.out.println("2. CSV Format");
                    System.out.println("3. Markdown Format");
                    System.out.println("0. Back");

                    int choice = inputHandler.getIntInRange("\nSelect format: ", 0, 3);
                    
                    switch (choice) {
                        case 0:
                            return;
                        case 1:
                            System.out.println("\n" + report.generateTextReport());
                            break;
                        case 2:
                            System.out.println("\nCSV Report:");
                            System.out.println(report.generateCSV());
                            break;
                        case 3:
                            System.out.println("\nMarkdown Report:");
                            System.out.println(report.generateMarkdown());
                            break;
                    }
                }
            
        } catch (Exception e) {
            logger.error("Error during sorting benchmark", e);
            System.out.println("Error running benchmark: " + e.getMessage());
        }
    }

    private void benchmarkSearchingAlgorithms() {
        logger.info("Starting searching algorithms benchmark");
        System.out.println("\nBenchmarking Binary Search...");

        try {
            SearchAlgorithm<Integer> binarySearch = new BinarySearch<>();
            BenchmarkResult result = benchmarkRunner.benchmarkSearching(binarySearch);
            
            System.out.println("\nBenchmark Complete!");
                
            PerformanceReport report = new PerformanceReport(result);
            
            while (true) {
                System.out.println("\n=== Report Format ===");
                System.out.println("1. Text Report (Console)");
                System.out.println("2. CSV Format");
                System.out.println("3. Markdown Format");
                System.out.println("0. Back");

                int choice = inputHandler.getIntInRange("\nSelect format: ", 0, 3);
                
                switch (choice) {
                    case 0:
                        return;
                    case 1:
                        System.out.println("\n" + report.generateTextReport());
                        break;
                    case 2:
                        System.out.println("\nCSV Report:");
                        System.out.println(report.generateCSV());
                        break;
                    case 3:
                        System.out.println("\nMarkdown Report:");
                        System.out.println(report.generateMarkdown());
                        break;
                }
            }
            
        } catch (Exception e) {
            logger.error("Error during searching benchmark", e);
            System.out.println("Error running benchmark: " + e.getMessage());
        }
    }
}
