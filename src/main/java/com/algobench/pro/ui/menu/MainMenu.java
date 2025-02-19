package com.algobench.pro.ui.menu;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Main menu handler for the AlgoBench Pro application.
 * Manages the display and execution of menu items.
 */
public class MainMenu {
    private final Map<Integer, MenuItem> menuItems;
    private final Scanner scanner;
    private boolean running;

    public MainMenu() {
        this.menuItems = new HashMap<>();
        this.scanner = new Scanner(System.in);
        this.running = true;
        initializeMenu();
    }

    private void initializeMenu() {
        // Add exit option
        menuItems.put(0, new MenuItem() {
            @Override
            public void execute() {
                running = false;
                System.out.println("Exiting AlgoBench Pro...");
            }

            @Override
            public String getDescription() {
                return "Exit";
            }
        });
    }

    /**
     * Adds a menu item to the main menu.
     * @param option The number to select this menu item
     * @param item The menu item to add
     */
    public void addMenuItem(int option, MenuItem item) {
        menuItems.put(option, item);
    }

    /**
     * Displays the menu and handles user input.
     */
    public void run() {
        while (running) {
            displayMenu();
            handleUserInput();
        }
        scanner.close();
    }

    private void displayMenu() {
        System.out.println("\n=== AlgoBench Pro ===");
        menuItems.forEach((key, item) -> 
            System.out.printf("%d. %s%n", key, item.getDescription()));
        System.out.print("\nEnter your choice: ");
    }

    private void handleUserInput() {
        try {
            String input = scanner.nextLine();
            int choice = Integer.parseInt(input);
            
            MenuItem selectedItem = menuItems.get(choice);
            if (selectedItem != null) {
                selectedItem.execute();
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }
}
