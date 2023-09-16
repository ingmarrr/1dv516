package src.core;

import java.util.List;

public interface IUnionFind {
  void union(int a, int b);

  boolean connected(int a, int b);

  int root(int a);

  IUnionFind clone();

  List<Integer> getIds();
}
