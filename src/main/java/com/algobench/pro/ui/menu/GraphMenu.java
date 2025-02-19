package com.algobench.pro.ui.menu;

import com.algobench.pro.algorithm.graph.DijkstraAlgorithm;
import com.algobench.pro.algorithm.graph.Graph;
import com.algobench.pro.ui.console.InputHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

/**
 * Menu handler for graph algorithm operations.
 */
public class GraphMenu implements MenuItem {
    private static final Logger logger = LogManager.getLogger(GraphMenu.class);
    private final InputHandler inputHandler;

    public GraphMenu() {
        this.inputHandler = new InputHandler();
    }

    @Override
    public void execute() {
        logger.info("Entering graph menu");

        while (true) {
            System.out.println("\n=== Graph Algorithms Menu ===");
            System.out.println("1. Dijkstra's Shortest Path");
            System.out.println("0. Return to Main Menu");

            int choice = inputHandler.getIntInRange("\nEnter your choice: ", 0, 1);

            switch (choice) {
                case 0:
                    logger.info("Exiting graph menu");
                    return;
                case 1:
                    runDijkstraAlgorithm();
                    break;
            }
        }
    }

    @Override
    public String getDescription() {
        return "Graph Algorithms";
    }

    private void runDijkstraAlgorithm() {
        logger.info("Starting Dijkstra's algorithm demo");

        Graph<String> graph = createDemoGraph();
        DijkstraAlgorithm<String> dijkstra = new DijkstraAlgorithm<>(graph);

        String startVertex = inputHandler.getValidInput(
            "Enter the starting vertex: ",
            String::trim,
            vertex -> graph.hasVertex(vertex)
        );

        try {
            Map<String, Double> shortestPaths = dijkstra.findShortestPaths(startVertex);

            System.out.println("\nShortest paths from vertex '" + startVertex + "':");
            shortestPaths.forEach((vertex, distance) -> {
                System.out.println("To vertex '" + vertex + "': " + distance);
            });

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            logger.error("Error running Dijkstra's algorithm: {}", e.getMessage());
        }
    }

    private Graph<String> createDemoGraph() {
        Graph<String> graph = new Graph<>();
        graph.addEdge("A", "B", 4);
        graph.addEdge("A", "C", 2);
        graph.addEdge("B", "D", 5);
        graph.addEdge("C", "D", 1);
        graph.addEdge("C", "E", 3);
        graph.addEdge("D", "E", 1);
        
        logger.debug("Demo graph created");
        return graph;
    }
}
