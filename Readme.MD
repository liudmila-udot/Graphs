## What is Graph

A graph is a data structure that consists of nodes (vertices) and edges that connect these nodes.

![](./images/graph.png)

## How to represent? Adjacency list
An adjacency list is a common way to represent a graph in computer science.
It's a data structure that stores information about the connections between vertices (nodes) in a graph. Here's how it works:

* Each vertex in the graph is associated with a list of its neighboring vertices.
* For an undirected graph, each vertex's adjacency list contains all the vertices that are directly connected to it by an edge.
* For a directed graph, each vertex's adjacency list contains only the vertices to which it has outgoing edges.

## Types of Graphs

### Undirected
An undirected graph is a type of graph in which edges do not have a direction associated with them.
In other words, the relationship between vertices (nodes) is symmetric; if there is an edge from vertex A to vertex B, there is also an edge from vertex B to vertex A.

![](images/undirected_graph.png)

### Directed

A directed graph, also known as a digraph, is a type of graph in which edges have a direction associated with them. 
This means that the relationship between vertices (nodes) is asymmetric; if there is an edge from vertex A to vertex B, 
it does not necessarily imply that there is an edge from vertex B to vertex A. 
Directed graphs can be further classified into two main categories based on the presence or absence of cycles.

![](images/directed_graph.png)

### Directed Acyclic Graph

A directed acyclic graph is a directed graph that does not contain any cycles. 
This means that it is impossible to traverse the graph starting from any vertex and following the direction of edges to 
return to the same vertex.
In other words, there are no loops in a DAG.

<img height="300px" src="images/directed_acyclic.png" width="200px"/>

### Directed Graph with Cycles

A directed graph with cycles is a directed graph that contains at least one cycle. 
A cycle is a path in the graph that starts and ends at the same vertex, following a sequence of directed edges. 
In other words, it is possible to traverse the graph from a vertex and return to the same vertex by following a sequence of directed edges.

### Weighted graphs

Each edge in a weighted graph has an associated weight. The weight can be of any metric, 
such as time, distance, size, etc. The most commonly seen “weighted map” in our daily life might be a city map.

<img height="260" src="images/weighted_graph.png" width="400"/>

### Tree
Trees have direction (parent / child relationships) and don't contain cycles. They fit with in the category of Directed Acyclic Graphs (or a DAG).
So Trees are DAGs with the restriction that a child can only have one parent.

![](images/tree.png)

## Terminologies

### In-degree, Out-degree
![](images/degree.png)

## Represent Graph in Java

```Java
Map<Integer, List<Integer>> adjList = new HashMap<>();
```

## Traverse

### BFS

* BFS explores a graph level by level, starting from a given vertex.
* It visits all the vertices at the current level before moving to the vertices at the next level.
* BFS is typically implemented using a queue data structure.
* It's useful for finding the shortest path in an unweighted graph.
* BFS in Java can be implemented using a queue, to keep track of vertices to visit next.

![](images/bfs_circle.png)

If we imagine first element being in center or circle and the next levels as its radius.

### DFS
* DFS explores a graph by going as deep as possible along each branch before backtracking.
* DFS is typically implemented using recursion or a stack data structure.
* It's useful for topological sorting, finding strongly connected components, and detecting cycles in a graph.

## Topological Sort
