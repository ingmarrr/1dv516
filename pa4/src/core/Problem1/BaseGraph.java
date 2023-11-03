package core.Problem1;

import core.Edge;

import java.util.ArrayList;
import java.util.List;

import static range.Range.range;

public class BaseGraph implements Graph {
  private List<List<Edge>> adj;

  public BaseGraph(int init) {
    adj = new ArrayList<>();
    while (adj.size() < init) adj.add(new ArrayList<>());
  }

  @Override
  public List<Edge> adjacent(int vtx) {
    if (vtx < adj.size()) return adj.get(vtx);
    return new ArrayList<>();
  }

  @Override
  public List<Edge> edges() {
    ArrayList<Edge> out = new ArrayList<>();
    for (var vtx : adj) {
      out.addAll(vtx);
    }
    return out;
  }

  @Override
  public List<Integer> vertices() {
    ArrayList<Integer> out = new ArrayList<>();
    for (var vtx : adj) {
      if (vtx.size() > 0) out.add(vtx.get(0).from());
    }
    return out;
  }

  @Override
  public int countEdges() {
    var out = 0;
    for (var neighbours : adj) {
      out += neighbours.size();
    }
    return out;
  }

  @Override
  public int countVertices() {
    return adj.size();
  }

  @Override
  public void addEdge(Edge edge) {
    ensureVtxExists(Math.max(edge.from(), edge.to()));
    adj.get(edge.from()).add(edge);
  }


  @Override
  public void addEdge(int from, int to) {
    ensureVtxExists(Math.max(from, to));
    adj.get(from).add(new Edge(from, to, 1.0));
  }

  @Override
  public void addEdge(int from, int to, double weight) {
    ensureVtxExists(Math.max(from, to));
    adj.get(from).add(new Edge(from, to, weight));
  }

  @Override
  public void removeEdge(Edge edge) {
    if (edge.from() < adj.size() && edge.to() < adj.size()) {
      adj.get(edge.from()).remove(edge);
    }
  }

  @Override
  public void removeEdge(int from, int to) {
    if (from < adj.size() && to < adj.size()) {
      adj.get(from).remove(new Edge(from, to, 1.0));
    }
  }
  @Override
  public int degree(int vtx) {
    return adj.get(vtx).size();
  }

  private void ensureVtxExists(int vtx) {
    while (adj.size() <= vtx) {
      adj.add(new ArrayList<>());
    }
  }
}
