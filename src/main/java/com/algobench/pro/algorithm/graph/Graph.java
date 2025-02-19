package com.algobench.pro.algorithm.graph;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@code Graph} class provides a generic implementation of a graph data structure.
 * <p>
 * It supports vertices of any type {@code T} and weighted edges. The graph is represented using an adjacency list,
 * where each vertex is associated with a list of its outgoing edges.
 *
 * <p><b>Type Parameter:</b></p>
 * <ul>
 *     <li>{@code <T>} - The type of vertices in this graph.</li>
 * </ul>
 *
 * @param <T> the type of vertices, can be any object
 * @version 1.0
 */
public class Graph<T> {
    private static final Logger logger = LogManager.getLogger(Graph.class);
    private final Map<T, List<Edge<T>>> adjacencyList;

    /**
     * Constructs an empty graph.
     * Initializes the adjacency list to store vertices and their edges.
     */
    public Graph() {
        this.adjacencyList = new HashMap<>();
        logger.debug("Graph initialized");
    }

    /**
     * Adds a vertex to the graph if it does not already exist.
     *
     * @param vertex The vertex to be added.
     * @throws IllegalArgumentException if the vertex is null.
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
     * Adds a weighted, directed edge to the graph.
     * Creates vertices for source and destination if they do not already exist.
     *
     * @param source      The starting vertex of the edge.
     * @param destination The ending vertex of the edge.
     * @param weight      The weight of the edge.
     * @throws IllegalArgumentException if source or destination vertices are null.
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
     * Returns the adjacency list representing the graph.
     *
     * @return A map where each vertex is mapped to a list of its outgoing edges.
     */
    public Map<T, List<Edge<T>>> getAdjacencyList() {
        return adjacencyList;
    }

    /**
     * Returns a set of all vertices in the graph.
     *
     * @return A Set containing all vertices in the graph.
     */
    public Set<T> getVertices() {
        return adjacencyList.keySet();
    }

    /**
     * Retrieves all edges originating from a specified vertex.
     *
     * @param vertex The vertex for which to retrieve edges.
     * @return A list of edges starting from the specified vertex. Returns an empty list if the vertex has no outgoing edges or does not exist.
     */
    public List<Edge<T>> getEdges(T vertex) {
        return adjacencyList.getOrDefault(vertex, new ArrayList<>());
    }

    /**
     * Checks if the graph contains a specific vertex.
     *
     * @param vertex The vertex to check for existence in the graph.
     * @return {@code true} if the vertex is in the graph, {@code false} otherwise.
     */
    public boolean hasVertex(T vertex) {
        return adjacencyList.containsKey(vertex);
    }

    /**
     * Checks if an edge exists between a source and a destination vertex.
     *
     * @param source      The source vertex.
     * @param destination The destination vertex.
     * @return {@code true} if an edge exists from the source to the destination, {@code false} otherwise.
     */
    public boolean hasEdge(T source, T destination) {
        if (!adjacencyList.containsKey(source)) {
            return false;
        }
        return adjacencyList.get(source).stream()
                             .anyMatch(edge -> edge.getDestination().equals(destination));
    }

    /**
     * Returns the total number of vertices in the graph.
     *
     * @return The number of vertices in the graph.
     */
    public int getVertexCount() {
        return adjacencyList.size();
    }

    /**
     * Returns the total number of edges in the graph.
     *
     * @return The number of edges in the graph.
     */
    public int getEdgeCount() {
        return adjacencyList.values().stream()
                             .mapToInt(List::size)
                             .sum();
    }

    /**
     * Removes all vertices and edges from the graph, effectively clearing it.
     */
    public void clear() {
        adjacencyList.clear();
        logger.debug("Graph cleared");
    }
}
