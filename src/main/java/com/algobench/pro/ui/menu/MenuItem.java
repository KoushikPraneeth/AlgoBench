package com.algobench.pro.ui.menu;

/**
 * Interface for menu items in the command-line interface.
 * Each menu item represents an executable action in the application.
 */
public interface MenuItem {
    /**
     * Executes the action associated with this menu item.
     */
    void execute();

    /**
     * Gets the description of this menu item.
     * @return A string describing what this menu item does
     */
    String getDescription();
}
