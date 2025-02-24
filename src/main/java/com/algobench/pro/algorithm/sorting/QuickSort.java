package com.algobench.pro.algorithm.sorting;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Implements the Quick Sort algorithm for sorting arrays.
 * <p>
 * QuickSort is a divide-and-conquer algorithm that works by selecting a 'pivot' element
 * from the array and partitioning the other elements into two sub-arrays, according to whether they are
 * less than or greater than the pivot. The sub-arrays are then sorted recursively.
 * <p>
 * This implementation includes logging at various stages for debugging and performance analysis.
 *
 * @param <T> the type of elements to be sorted, must implement Comparable
 * @version 1.0
 * @see SortingAlgorithm
 */
public class QuickSort<T extends Comparable<T>> implements SortingAlgorithm<T> {
    private static final Logger logger = LogManager.getLogger(QuickSort.class);

    @Override
    public void sort(T[] array) {
        validateInput(array);
        logger.info("Starting QuickSort on array of length: {}", array.length);
        quickSort(array, 0, array.length - 1);
        logger.info("QuickSort completed");
    }

    @Override
    public String getName() {
        return "Quick Sort";
    }

    private void quickSort(T[] array, int low, int high) {
        if (low < high) {
            logger.debug("Partitioning array between indices {} and {}", low, high);
            int pivotIndex = partition(array, low, high);
            logger.debug("Pivot element placed at index: {}", pivotIndex);

            // Recursively sort the sub-arrays
            quickSort(array, low, pivotIndex - 1);
            quickSort(array, pivotIndex + 1, high);
        }
    }

    private int partition(T[] array, int low, int high) {
        // Choose the rightmost element as pivot
        T pivot = array[high];
        logger.debug("Selected pivot value: {}", pivot);

        // Index of smaller element
        int i = (low - 1);

        // Compare each element with pivot
        for (int j = low; j < high; j++) {
            if (array[j].compareTo(pivot) <= 0) {
                i++;
                swap(array, i, j);
            }
        }

        // Place pivot in its correct position
        swap(array, i + 1, high);
        return i + 1;
    }

    private void swap(T[] array, int i, int j) {
        if (i != j) {
            logger.debug("Swapping elements at indices {} and {}", i, j);
            T temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }
}
