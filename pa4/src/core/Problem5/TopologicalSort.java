package core.Problem5;

import core.Problem1.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TopologicalSort {

  private final boolean[] visited;
  private final Stack<Integer> stack;

  public TopologicalSort(Graph graph) {
    visited = new boolean[graph.countVertices()];
    stack = new Stack<>();
    for (int vertex : graph.vertices()) {
      if (!visited[vertex]) dfs(graph, vertex);
    }
  }

  public List<Integer> order() {
    ArrayList<Integer> out = new ArrayList<>();
    while (!stack.isEmpty()) out.add(stack.pop());
    return out;
  }

  private void dfs(Graph graph, int vertex) {
    visited[vertex] = true;
    for (var edge : graph.adjacent(vertex)) {
      if (!visited[edge.to()]) dfs(graph, edge.to());
    }
    stack.push(vertex);
  }

}
