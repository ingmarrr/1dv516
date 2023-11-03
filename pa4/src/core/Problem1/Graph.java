package core.Problem1;

import core.Edge;

import java.util.List;

/**
 * #edges,
 * #vertices,
 * add edge,
 * remove edge,
 * degree
 */
public interface Graph {
  List<Edge> adjacent(int vtx);
  List<Edge> edges();
  List<Integer> vertices();
  int countEdges();
  int countVertices();
  void addEdge(Edge edge);
  void addEdge(int from, int to);
  void addEdge(int from, int to, double weight);
  void removeEdge(Edge edge);
  void removeEdge(int from, int to);
  int degree(int vtx);
}
