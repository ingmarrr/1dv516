package core.Problem4;

import core.Edge;
import core.Problem1.DirectedGraph;
import core.Problem1.Graph;

import java.util.Arrays;
import java.util.LinkedList;

import static range.Range.range;

public class BellmanFord {

  private final DirectedGraph graph;
  public final double[] distanceTo;
  private final Edge[] lastEdgeTo;
  private final LinkedList<Integer> queue;
  private final boolean[] onqueue;

  public BellmanFord(DirectedGraph graph, int vertex) {
    this.graph = graph;
    lastEdgeTo = new Edge[graph.countVertices()];
    distanceTo = new double[graph.countVertices()];
    Arrays.fill(distanceTo, Double.MAX_VALUE);
    distanceTo[vertex] = 0;
    queue = new LinkedList<>();
    queue.addLast(vertex);
    onqueue = new boolean[graph.countVertices()];

    while (!queue.isEmpty()) {
      var vtx = queue.pop();
      onqueue[vtx] = false;
      relax(graph, vtx);
    }
  }

  private void relax(Graph graph, int vertex) {
    for (final Edge edge : graph.adjacent(vertex)) {
      if (distanceTo[edge.to()] > distanceTo[edge.from()] + edge.weight()) {
        distanceTo[edge.to()] = distanceTo[edge.from()] + edge.weight();
        lastEdgeTo[edge.to()] = edge;
        if (!onqueue[edge.to()]) {
          queue.addFirst(edge.to());
          onqueue[edge.to()] = true;
        }
      }
    }
  }

  public double distanceTo(int vertex) {
    return distanceTo[vertex];
  }
}
