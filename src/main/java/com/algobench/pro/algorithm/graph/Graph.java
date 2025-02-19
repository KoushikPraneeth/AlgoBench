package com.algobench.pro.algorithm.graph;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generic Graph data structure implementation.
 * @param <T> The type of vertices in the graph
 */
public class Graph<T> {
    private static final Logger logger = LogManager.getLogger(Graph.class);
    private final Map<T, List<Edge<T>>> adjacencyList;

    public Graph() {
        this.adjacencyList = new HashMap<>();
        logger.debug("Graph initialized");
    }

    /**
     * Adds a vertex to the graph.
     * @param vertex The vertex to add
     */
    public void addVertex(T vertex) {
        if (vertex == null) {
            logger.error("Vertex cannot be null");
            throw new IllegalArgumentException("Vertex cannot be null");
        }
        adjacencyList.putIfAbsent(vertex, new ArrayList<>());
        logger.debug("Vertex {} added to graph", vertex);
    }

    /**
     * Adds an edge to the graph.
     * @param source The source vertex
     * @param destination The destination vertex
     * @param weight The weight of the edge
     */
    public void addEdge(T source, T destination, double weight) {
        if (source == null || destination == null) {
            logger.error("Source and destination vertices cannot be null");
            throw new IllegalArgumentException("Vertices cannot be null");
        }
        if (!adjacencyList.containsKey(source)) {
            addVertex(source);
        }
        if (!adjacencyList.containsKey(destination)) {
            addVertex(destination);
        }

        Edge<T> edge = new Edge<>(destination, weight);
        adjacencyList.get(source).add(edge);
        logger.debug("Edge added from {} to {} with weight {}", source, destination, weight);
    }

    /**
     * Gets the adjacency list of the graph.
     * @return Map representing the adjacency list
     */
    public Map<T, List<Edge<T>>> getAdjacencyList() {
        return adjacencyList;
    }

    /**
     * Gets the vertices of the graph.
     * @return Set of vertices
     */
    public Set<T> getVertices() {
        return adjacencyList.keySet();
    }

    /**
     * Gets the edges originating from a vertex.
     * @param vertex The source vertex
     * @return List of edges from the vertex
     */
    public List<Edge<T>> getEdges(T vertex) {
        return adjacencyList.getOrDefault(vertex, new ArrayList<>());
    }

    /**
     * Checks if the graph contains a vertex.
     * @param vertex The vertex to check
     * @return true if the vertex exists, false otherwise
     */
    public boolean hasVertex(T vertex) {
        return adjacencyList.containsKey(vertex);
    }

    /**
     * Checks if there is an edge between two vertices.
     * @param source The source vertex
     * @param destination The destination vertex
     * @return true if an edge exists, false otherwise
     */
    public boolean hasEdge(T source, T destination) {
        if (!adjacencyList.containsKey(source)) {
            return false;
        }
        return adjacencyList.get(source).stream()
                             .anyMatch(edge -> edge.getDestination().equals(destination));
    }

    /**
     * Returns the number of vertices in the graph.
     * @return Vertex count
     */
    public int getVertexCount() {
        return adjacencyList.size();
    }

    /**
     * Returns the number of edges in the graph.
     * @return Edge count
     */
    public int getEdgeCount() {
        return adjacencyList.values().stream()
                             .mapToInt(List::size)
                             .sum();
    }

    /**
     * Clears all vertices and edges from the graph.
     */
    public void clear() {
        adjacencyList.clear();
        logger.debug("Graph cleared");
    }
}
