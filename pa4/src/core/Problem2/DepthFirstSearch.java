package core.Problem2;

import core.Edge;
import core.Problem1.Graph;

public class DepthFirstSearch {

  private final Graph graph;
  private boolean[] marked;

  public DepthFirstSearch(Graph g, int root) {
    graph = g;
    marked = new boolean[graph.countVertices()];
    dfs(root);
  }

  private void dfs(int vtx) {
    marked[vtx] = true;
    for (Edge edge : graph.adjacent(vtx)) {
      if (!marked[edge.to()]) dfs(edge.to());
    }
  }

  public boolean hasPathTo(int vtx) {
    return marked[vtx];
  }

}
