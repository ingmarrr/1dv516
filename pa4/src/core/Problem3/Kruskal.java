package core.Problem3;

import core.Edge;
import core.Problem1.Graph;

import java.util.ArrayList;

public class Kruskal {

  private final MinHeap<Edge> heap;
  private final UnionFind uf;
  private final ArrayList<Edge> mst;

  public ArrayList<Edge> getMst() {
    return mst;
  }

  public Kruskal(Graph graph) {
    heap = new MinHeap<>(2, Edge.class);
    uf = new UnionFind(graph.countVertices());
    mst = new ArrayList<>();
    for (Edge edge : graph.edges()) heap.push(edge);

    while (heap.hasSome() && mst.size() < graph.countVertices() - 1) {
      var edge = heap.popMin().get();
      if (!uf.connected(edge.from(), edge.to())) {
        uf.union(edge.from(), edge.to());
        mst.add(edge);
      }
    }
  }
}
