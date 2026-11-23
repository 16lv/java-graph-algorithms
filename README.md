# java-graph-algorithms

Clean, test-verified **Java 21** implementations of graph traversal, shortest-path routing, sorting, order statistics, dynamic programming, and core data structures without third-party algorithm libraries.

## Overview

Built as an algorithmic reference library and benchmark harness using standard Java collections and primitive arrays so every pointer mutation, heap swap, and complexity bound stays explicit and inspectable.

## Implemented Components

| Component | Highlights | Complexity |
| :--- | :--- | :--- |
| `IndexedMinHeap<K>` | Binary min-heap + index map for decrease-key, `peekMin()`, `priorityOf()`, NaN/null guards | `O(log V)` update / poll |
| `GraphAlgorithms` | BFS, DFS, Dijkstra shortest path, Bellman-Ford (negative weights + cycle detection), Kahn topological sort + cycle check, Kruskal MST | `O((V + E) log V)` / `O(VE)` |
| `DisjointSetUnion` | Union-Find with path compression & union-by-rank | `O(alpha(N))` amortised |
| `LruCache<K, V>` | `O(1)` lookup/eviction with sentinel doubly-linked list + `HashMap` | `O(1)` get / put / remove |
| `SortingAndSelection` | Iterative Bottom-Up MergeSort, QuickSort, Binary Search, `lowerBound`, QuickSelect | `O(N log N)` / `O(N)` expected |
| `DynamicProgramming` | Rolling-array Levenshtein edit distance & 0/1 Knapsack | `O(min(N,M))` / `O(W)` space |

## Technology Stack

- Java 21 LTS
- Maven 3.9+
- JUnit 5 (Jupiter)

## Build, Run & Benchmarks

```bash
mvn -B test
mvn -B -q compile exec:java -Dexec.mainClass="agency.shss7.algolab.benchmark.BenchmarkRunner"
```

## Technical Implementation Details

- `GraphAlgorithms.Edge` rejects negative, `NaN`, and infinite weights up front (`!(weight >= 0.0) || Double.isInfinite(weight)`) so priority queues never compare invalid IEEE-754 weights.
- `SortingAndSelection.mergeSort` runs iteratively bottom-up to keep recursion stack depth at `O(1)`, and `DynamicProgramming` reuses rolling 1D arrays rather than allocating full 2D DP matrices.

## Project Structure

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
├── src/test/java/agency/shss7/algolab/
│   └── AlgorithmLaboratoryTest.java
└── pom.xml
```

## Testing

```bash
mvn -B test
```

## Known Limitations & Future Improvements

- `LruCache` is intentionally single-threaded without internal locking; concurrent workloads require external synchronisation.
- Dijkstra assumes non-negative edge weights (`Edge` rejects `< 0` and `NaN`); use `GraphAlgorithms.bellmanFord()` with `SignedEdge` when negative weights or negative-cycle detection are required.
