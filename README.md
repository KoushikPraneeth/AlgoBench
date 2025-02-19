# AlgoBench Pro

AlgoBench Pro is a comprehensive Java application for benchmarking and visualizing common algorithms and data structures. It provides an interactive command-line interface to test and compare the performance of various algorithms.

## Features

- **Sorting Algorithms**
  - Quick Sort
  - (More sorting algorithms to be added)

- **Searching Algorithms**
  - Binary Search
  - (More searching algorithms to be added)

- **Graph Algorithms**
  - Dijkstra's Algorithm
  - (More graph algorithms to be added)

- **Data Structures**
  - Binary Search Tree
  - Graph (Adjacency List implementation)
  - (More data structures to be added)

## Installation

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/algobench-pro.git
   ```

2. Navigate to the project directory:
   ```bash
   cd algobench-pro
   ```

3. Build the project:
   ```bash
   mvn clean install
   ```

## Usage

### Running the Application
```bash
mvn exec:java -Dexec.mainClass="com.algobench.pro.Application"
```

### Menu Options
1. **Sorting Algorithms**
   - Choose different sorting algorithms
   - Specify input size
   - View performance metrics

2. **Searching Algorithms**
   - Test binary search
   - Compare performance with different input sizes

3. **Graph Algorithms**
   - Create and manipulate graphs
   - Run shortest path algorithms

## Benchmarking

The application provides comprehensive benchmarking capabilities:

- **Time Measurement**
  - Average execution time
  - Best and worst-case times
  - Standard deviation

- **Memory Usage**
  - Peak memory consumption
  - Memory usage trends

- **Visualization**
  - ASCII charts for performance comparison
  - Progress bars for long-running operations
  - Formatted tables for results

### Sample Benchmark
```
Performance Report - Quick Sort
Generated: 2025-02-20 00:52:25

Input Size | Avg Time (ms) | Memory (MB)
-----------|---------------|------------
1000       | 0.52         | 2.1
10000      | 6.84         | 4.3
100000     | 84.62        | 8.7
```

## Contributing

1. Fork the repository
2. Create your feature branch:
   ```bash
   git checkout -b feature/NewFeature
   ```
3. Commit your changes:
   ```bash
   git commit -m 'Add NewFeature'
   ```
4. Push to the branch:
   ```bash
   git push origin feature/NewFeature
   ```
5. Submit a pull request

### Development Guidelines
- Follow Java coding conventions
- Add appropriate JavaDoc comments
- Maintain test coverage above 80%
- Update documentation for new features

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- Thanks to all contributors
- Inspired by classic algorithm benchmarking tools
- Built with modern Java practices and design patterns
