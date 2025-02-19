package com.algobench.pro.ui.menu;

import com.algobench.pro.algorithm.searching.BinarySearch;
import com.algobench.pro.algorithm.searching.SearchAlgorithm;
import com.algobench.pro.ui.console.InputHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

/**
 * Menu handler for searching algorithm operations.
 */
public class SearchingMenu implements MenuItem {
    private static final Logger logger = LogManager.getLogger(SearchingMenu.class);
    private final InputHandler inputHandler;

    public SearchingMenu() {
        this.inputHandler = new InputHandler();
    }

    @Override
    public void execute() {
        logger.info("Entering searching menu");

        // Get array size from user
        int size = inputHandler.getPositiveInt("Enter the size of the sorted array: ");

        // Create and sort array
        Integer[] array = new Integer[size];
        for (int i = 0; i < size; i++) {
            array[i] = i + 1; // Create a sorted array from 1 to size
        }

        // Display array
        System.out.println("\nSorted array:");
        displayArray(array);

        // Get search target from user
        int target = inputHandler.getPositiveInt("Enter the number to search for: ");

        // Create and execute search algorithm
        SearchAlgorithm<Integer> algorithm = new BinarySearch<>();

        try {
            // Search and measure time
            long startTime = System.nanoTime();
            int result = algorithm.search(array, target);
            long endTime = System.nanoTime();

            // Display results
            if (result != -1) {
                System.out.printf("\nFound %d at index: %d%n", target, result);
            } else {
                System.out.printf("\n%d was not found in the array%n", target);
            }

            // Display performance metrics
            double timeInMillis = (endTime - startTime) / 1_000_000.0;
            System.out.printf("Time taken: %.2f ms%n", timeInMillis);
            logger.info("Search completed in {} ms", timeInMillis);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            logger.error("Search failed: {}", e.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "Run Searching Algorithms";
    }

    private void displayArray(Integer[] array) {
        // For large arrays, only show first and last few elements
        if (array.length > 20) {
            System.out.print(Arrays.toString(Arrays.copyOfRange(array, 0, 10)));
            System.out.print(" ... ");
            System.out.println(Arrays.toString(Arrays.copyOfRange(array, array.length - 10, array.length)));
        } else {
            System.out.println(Arrays.toString(array));
        }
    }
}
