import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.util.Pair;

/**
 * https://www.careercup.com/question?id=5631096870928384
 */
public class CurrencyExchangeRate {

    static class Graph {

        int nodeNumber;
        Map<String, List<Pair<String, Float>>> adj; // Adjacency list

        Graph(int nodeNumber) {
            this.nodeNumber = nodeNumber;
            adj = new HashMap<>();
        }

        void addEdge(String u, String v, float rate) {
            if (!adj.containsKey(u)) {
                adj.put(u, new ArrayList<>());
            }
            if (!adj.containsKey(v)) {
                adj.put(v, new ArrayList<>());
            }
            adj.get(u).add(new Pair<>(v, rate));
            adj.get(v).add(new Pair<>(u, rate));
        }
    }

    public static List<Pair<String, Float>> bfsShortestPath(Graph graph, String rootNode, String endNode) {
        Set<Pair<String, Float>> visited = new HashSet<>();
        LinkedList<Pair<String, Float>> stack = new LinkedList<>();

        Queue<Pair<String, Float>> queue = new LinkedList<>();

        queue.add(new Pair<>(rootNode, 1f));
        stack.push(new Pair<>(rootNode, 1f));
        visited.add(new Pair<>(rootNode, 1f));

        while (!queue.isEmpty()) {
            Pair<String, Float> node = queue.poll();

            List<Pair<String, Float>> unvisited = getUnvisitedChildNodes(graph, visited, node);

            for (Pair<String, Float> unvisitedChild : unvisited) {
                queue.add(unvisitedChild);
                stack.push(unvisitedChild);
                visited.add(unvisitedChild);
                if (Objects.equals(unvisitedChild.getKey(), endNode)) {
                    queue.clear();
                    break;
                }
            }
        }

        List<Pair<String, Float>> ret = new ArrayList<>();
        ret.add(stack.peek());

        Pair<String, Float> node;
        Pair<String, Float> currentNode = stack.peek();

        while (!stack.isEmpty()) {
            node = stack.pop();
            if (graph.adj.get(currentNode.getKey()).stream().map(Pair::getKey)
                    .collect(Collectors.toSet()).contains(node.getKey())) {
                ret.add(node);
                currentNode = node;
            }
        }

        return ret;
    }

    public static List<Pair<String, Float>> getUnvisitedChildNodes(Graph graph, Set<Pair<String, Float>> visited, Pair<String, Float> parent) {
        Set<Pair<String, Float>> copy = new HashSet<>(graph.adj.get(parent.getKey()));
        copy.removeAll(visited);
        return new ArrayList<Pair<String, Float>>(copy);
    }


    /**
     * Given a list of currency exchange rates like this:
     * EUR/USD => 1.2
     * USD/GBP => 0.75
     * GBP/AUD => 1.7
     * AUD/JPY => 90
     * GBP/JPY => 150
     * JPY/INR => 0.6
     * <p>
     * write a method convert
     * <p>
     * For example, convert(EUR, 100, INR)
     * <p>
     * The method should minimize the number of intermediate conversions
     *
     * @param sourceCurrency
     * @param amount
     * @param destCurrency
     * @return
     */
    double convert(String sourceCurrency, double amount, String destCurrency) {
        return 0.0;
    }

    public static void main(String[] args) {
        Graph graph = new Graph(6);
        graph.addEdge("EUR", "USD", 1.2f);
        graph.addEdge("USD", "GBP", 0.75f);
        graph.addEdge("GPB", "AUD", 1.7f);
        graph.addEdge("AUD", "GPY", 90f);
        graph.addEdge("GBP", "GPY", 150f);
        graph.addEdge("GPY", "INR", 0.6f);

        System.out.println(bfsShortestPath(graph, "EUR", "INR"));
    }
}
