package src.core;

public interface IUnionFind {
  void union(int a, int b);

  boolean connected(int a, int b);

  int root(int a);
}
