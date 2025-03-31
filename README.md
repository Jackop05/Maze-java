# Maze Generator and Solver in Java

[![Java](https://img.shields.io/badge/Java-17%2B-blue)](https://java.com)
[![License](https://img.shields.io/badge/License-MIT-green)](https://opensource.org/licenses/MIT)

A Java implementation of maze generation and solving algorithms with visualization capabilities.

<img width="1076" alt="image" src="https://github.com/user-attachments/assets/b9d32ab3-be38-4880-b2e7-b5140b76c11b" />


## Features

- **Maze Generation**:
  - Randomized Depth-First Search (DFS)
  - Prim's Algorithm
  - Recursive Division
- **Maze Solving**:
  - Depth-First Search (DFS)
  - Breadth-First Search (BFS)
  - A* Search Algorithm
- **Visualization**:
  - Real-time generation/solving display
  - Adjustable animation speed
  - Color-coded path visualization

## 🤖 Algorithms Implemented

### Generation Algorithms
| Algorithm       | Complexity | Characteristics               |
|-----------------|------------|-------------------------------|
| Randomized DFS  | O(V + E)   | Creates long, winding paths   |
| Prim's          | O(E log V) | Produces more uniform mazes   |
| Recursive Div   | O(n log n) | Creates large open areas      |

### Solving Algorithms
| Algorithm | Complexity | Optimal Solution |
|-----------|------------|------------------|
| DFS       | O(V + E)   | ❌ No            |
| BFS       | O(V + E)   | ✔️ Yes           |
| A*        | O(E)       | ✔️ Yes           |
