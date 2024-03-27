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

```Java
    private Map<Integer, List<Integer>> adjList; // Map to represent adjacency list

    // Constructor
    public Graph() {
        adjList = new HashMap<>();
    }

    // Function to add an edge to the graph
    public void addEdge(int v, int w) {
        adjList.putIfAbsent(v, new ArrayList<>());
        adjList.get(v).add(w); // Add w to v's list
    }
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

```Java
// Function to perform BFS traversal starting from a given vertex
    public void bfs(Map<Integer, List<Integer>> adjList, int startVertex) {
        Set<Integer> visited = new HashSet<>(); // Set to keep track of visited vertices
        LinkedList<Integer> queue = new LinkedList<>(); // Queue for BFS

        visited.add(startVertex); // Mark the start vertex as visited
        queue.add(startVertex); // Enqueue the start vertex

        while (!queue.isEmpty()) {
            int currentVertex = queue.poll(); // Dequeue a vertex
            System.out.print(currentVertex + " ");

            // Visit all adjacent vertices of the dequeued vertex
            for (int neighbor : adjList.getOrDefault(currentVertex, Collections.emptyList())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
    }
```
### DFS
* DFS explores a graph by going as deep as possible along each branch before backtracking.
* DFS is typically implemented using recursion or a stack data structure.
* It's useful for topological sorting, finding strongly connected components, and detecting cycles in a graph.

Recursive
```Java
    // Function to perform DFS traversal starting from a given vertex
    public void dfs(Map<Integer, List<Integer>> adjList, int startVertex) {
        Set<Integer> visited = new HashSet<>(); // Set to keep track of visited vertices
        dfs(adjList, startVertex, visited);
    }

    // Recursive utility function for DFS traversal
    private void dfs(Map<Integer, List<Integer>> adjList, int vertex, Set<Integer> visited) {
        visited.add(vertex);
        System.out.print(vertex + " ");

        // Recursively visit all adjacent vertices of the current vertex
        for (int neighbor : adjList.getOrDefault(vertex, Collections.emptyList())) {
            if (!visited.contains(neighbor)) {
                dfs(adjList, neighbor, visited);
            }
        }
    }
```

Using Stack
```Java
 // Function to perform DFS traversal starting from a given vertex
    public void dfs(Map<Integer, List<Integer>> adjList, int startVertex) {
        Set<Integer> visited = new HashSet<>(); // Set to keep track of visited vertices
        Stack<Integer> stack = new Stack<>(); // Stack for DFS

        stack.push(startVertex); // Push the start vertex onto the stack

        while (!stack.isEmpty()) {
            int currentVertex = stack.pop(); // Pop a vertex from the stack

            // Visit the current vertex if it has not been visited before
            if (!visited.contains(currentVertex)) {
                System.out.print(currentVertex + " ");
                visited.add(currentVertex);

                // Push all adjacent vertices of the current vertex onto the stack
                for (int neighbor : adjList.getOrDefault(currentVertex, Collections.emptyList())) {
                    if (!visited.contains(neighbor)) {
                        stack.push(neighbor);
                    }
                }
            }
        }
    }
```

Test
```Java
public static void main(String[] args) {
        Graph graph = new Graph();
        graph.addEdge(0, 1);
        graph.addEdge(0, 4);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(1, 4);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);

        System.out.print("BFS traversal starting from vertex 0: ");
        bsf(graph.getAdjList(), 0);
        System.out.println();

        System.out.print("DFS traversal starting from vertex 0: ");
        dsf(graph.getAdjList(), 0);
    }
```

## Number of Connected Components

We can do DSF of BSF from each key in adjacency list and store visited into shared set:
when adjacency list key is not visited yet - we increase number of connected components by one.


```java
    public int numberOfConnectedComponentsBfs(Map<Integer, List<Integer>> adj) {
        Set<Integer> visited = new HashSet<>();
        int ret = 0;
        for (Integer key : adj.keySet()) {
            if (!visited.contains(key)) {
                ret++;
            }
            bfs(adj, key, visited);
        }
        return ret;
    }

    public void bfs(Map<Integer, List<Integer>> adj, int startVertex, Set<Integer> visited) {
        LinkedList<Integer> queue = new LinkedList<>(); // Queue for BFS

        visited.add(startVertex); // Mark the start vertex as visited
        queue.add(startVertex); // Enqueue the start vertex

        while (!queue.isEmpty()) {
            int currentVertex = queue.poll(); // Dequeue a vertex

            // Visit all adjacent vertices of the dequeued vertex
            for (int neighbor : adj.getOrDefault(currentVertex, Collections.emptyList())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
    }
```    

## Topological Sort
