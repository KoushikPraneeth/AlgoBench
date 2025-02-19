package com.algobench;

import com.algobench.algorithms.BubbleSort;
import com.algobench.core.ConsoleMenu;

/**
 * Main entry point for AlgoBench Pro.
 * Sets up and runs the console menu with available algorithms.
 */
public class Main {
    public static void main(String[] args) {
        ConsoleMenu menu = new ConsoleMenu();
        
        // Register available algorithms
        menu.registerAlgorithm(new BubbleSort());
        
        // Start the console menu
        menu.start();
    }
}