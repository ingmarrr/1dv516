package core.Problem2;

import core.Edge;
import core.Problem1.Graph;

import java.util.LinkedList;

public class BreadthFirstSearch {
  private final Graph graph;
  private final int rootVertex;
  private final boolean[] marked;

  public BreadthFirstSearch(Graph g, int root) {
    graph = g;
    rootVertex = root;
    marked = new boolean[graph.countVertices()];
    bfs(rootVertex);
  }

  private void bfs(int vtx) {
    var queue = new LinkedList<Integer>();
    queue.addLast(vtx);
    marked[vtx] = true;
    while (!queue.isEmpty()) {
      var toHandle = queue.removeFirst();
      for (Edge edge : graph.adjacent(toHandle)) {
          if (!marked[edge.to()]) {
            marked[edge.to()] = true;
            queue.addLast(edge.to());
          }
      }
    }
  }

  public boolean hasPathTo(int vtx) {
    return marked[vtx];
  }
}
