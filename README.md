# java-graph-algorithms

Java 21 library of graph search, shortest-path routing, minimum spanning tree, sorting, dynamic programming, and generic data structures with JUnit 5 verification and an empirical CLI benchmark runner.

## Implemented Components

- **Graph algorithms (`GraphAlgorithms`)**: Iterative BFS and DFS with start-vertex validation, Dijkstra's shortest path backed by `IndexedMinHeap`, Kahn's topological sort with cycle detection, and Kruskal's MST using `DisjointSetUnion`.
- **Structures (`IndexedMinHeap`, `DisjointSetUnion`, `LruCache`)**: `IndexedMinHeap` with `O(log V)` in-place priority decrease, `peekMin()`, `priorityOf()`, and null/NaN guards; sentinel-bounded `LruCache` with `O(1)` lookup, promotion, eviction, and `remove()`.
- **Sorting & selection (`SortingAndSelection`)**: Iterative bottom-up MergeSort, QuickSort, binary search, `lowerBound`, and Hoare QuickSelect.
- **Dynamic programming (`DynamicProgramming`)**: Space-optimised Levenshtein edit distance and rolling 1D 0/1 knapsack solver.

## Structure

```text
java-graph-algorithms/
├── src/main/java/agency/shss7/algolab/
│   ├── benchmark/BenchmarkRunner.java
│   ├── dp/DynamicProgramming.java
│   ├── graph/GraphAlgorithms.java
│   ├── sorting/SortingAndSelection.java
│   └── structures/
│       ├── DisjointSetUnion.java
│       ├── IndexedMinHeap.java
│       └── LruCache.java
└── src/test/java/agency/shss7/algolab/AlgorithmLaboratoryTest.java
```

## Build & Run

```bash
mvn test
mvn -q package
java -jar target/java-algorithms-lab-1.2.0.jar
```
