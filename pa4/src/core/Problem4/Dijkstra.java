package core.Problem4;

import core.Edge;
import core.Problem1.Graph;
import core.Problem3.MinHeap;
import core.Vertex;

import java.util.Arrays;

public class Dijkstra {

  private final MinHeap<Vertex> heap;
  private final Edge[] lastEdgeTo;
  private final double[] distanceTo;


  public Dijkstra(Graph graph, int root) {
    heap = new MinHeap(2, Vertex.class);
    lastEdgeTo = new Edge[graph.countVertices()];
    distanceTo = new double[graph.countVertices()];
    Arrays.fill(distanceTo, Double.MAX_VALUE);
    distanceTo[root] = 0;
    heap.push(new Vertex(0.0, root));
    while (heap.hasSome()) {
      var vertex = heap.popMin();
      for (var edge : graph.adjacent(vertex.get().p())) {
        relax(edge);
      }
    }
  }

  private void relax(Edge edge) {
    if (distanceTo[edge.to()] > distanceTo[edge.from()] + edge.weight()) {
      distanceTo[edge.to()] = distanceTo[edge.from()] + edge.weight();
      lastEdgeTo[edge.to()] = edge;
      heap.updateWhere(
          vtx -> vtx.p() == edge.to(),
          new Vertex(distanceTo[edge.to()], edge.to())
      );
    }
  }

  public double distanceTo(int vtx) {
    return distanceTo[vtx];
  }

}
