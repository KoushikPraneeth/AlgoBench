package com.algobench.pro.ui.menu;

import com.algobench.pro.algorithm.sorting.QuickSort;
import com.algobench.pro.algorithm.sorting.SortingAlgorithm;
import com.algobench.pro.ui.console.InputHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Random;

/**
 * Menu handler for sorting algorithm operations.
 */
public class SortingMenu implements MenuItem {
    private static final Logger logger = LogManager.getLogger(SortingMenu.class);
    private final InputHandler inputHandler;
    private final Random random;

    public SortingMenu() {
        this.inputHandler = new InputHandler();
        this.random = new Random();
    }

    @Override
    public void execute() {
        logger.info("Entering sorting menu");
        
        // Get array size from user
        int size = inputHandler.getPositiveInt("Enter the size of the array to sort: ");
        
        // Generate random array
        Integer[] array = generateRandomArray(size);
        
        // Display original array
        System.out.println("\nOriginal array:");
        displayArray(array);
        
        // Create and execute sorting algorithm
        SortingAlgorithm<Integer> algorithm = new QuickSort<>();
        
        // Sort and measure time
        long startTime = System.nanoTime();
        algorithm.sort(array);
        long endTime = System.nanoTime();
        
        // Display results
        System.out.println("\nSorted array:");
        displayArray(array);
        
        // Display performance metrics
        double timeInMillis = (endTime - startTime) / 1_000_000.0;
        System.out.printf("\nTime taken: %.2f ms%n", timeInMillis);
        logger.info("Sorting completed in {} ms", timeInMillis);
    }

    @Override
    public String getDescription() {
        return "Run Sorting Algorithms";
    }

    private Integer[] generateRandomArray(int size) {
        Integer[] array = new Integer[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(1000); // Random numbers between 0 and 999
        }
        logger.debug("Generated random array of size {}", size);
        return array;
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
