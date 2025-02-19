package com.algobench.algorithms;

import com.algobench.core.SortingAlgorithm;

/**
 * Implementation of the Bubble Sort algorithm.
 * Used as a test case for the AlgoBench Pro framework.
 */
public class BubbleSort extends SortingAlgorithm {

    @Override
    protected int[] sort(int[] array) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (compare(array[j], array[j + 1]) > 0) {
                    swap(array, j, j + 1);
                }
            }
        }
        return array;
    }

    @Override
    public String getName() {
        return "Bubble Sort";
    }
}