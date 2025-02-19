package com.algobench.pro;

import com.algobench.pro.ui.menu.MainMenu;
import com.algobench.pro.ui.menu.MenuItem;
import com.algobench.pro.ui.menu.SearchingMenu;
import com.algobench.pro.ui.menu.SortingMenu;
import com.algobench.pro.ui.menu.BenchmarkMenu;
import com.algobench.pro.util.config.ConfigurationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Main entry point for the AlgoBench Pro application.
 */
public class Application {
    private static final Logger logger = LogManager.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting AlgoBench Pro");
        
        // Initialize configuration
        ConfigurationManager config = ConfigurationManager.getInstance();
        logger.info("Configuration initialized");

        // Create and configure main menu
        MainMenu mainMenu = new MainMenu();
        initializeMenuItems(mainMenu);
        
        // Start the application
        try {
            mainMenu.run();
            logger.info("Application terminated normally");
        } catch (Exception e) {
            logger.error("Unexpected error occurred", e);
            System.err.println("An unexpected error occurred. Check logs for details.");
        }
    }

    private static void initializeMenuItems(MainMenu mainMenu) {
        // Add sorting algorithms menu
        mainMenu.addMenuItem(1, new SortingMenu());

        // Add searching algorithms menu
        mainMenu.addMenuItem(2, new SearchingMenu());

        // Add graph algorithms menu item
        mainMenu.addMenuItem(3, new MenuItem() {
            @Override
            public void execute() {
                System.out.println("Graph algorithms - Coming soon!");
            }

            @Override
            public String getDescription() {
                return "Graph Algorithms";
            }
        });

        // Add benchmark menu
        mainMenu.addMenuItem(4, new BenchmarkMenu());

        logger.info("Menu items initialized");
    }
}
