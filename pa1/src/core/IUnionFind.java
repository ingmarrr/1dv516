package src.core;

public interface IUnionFind {
  public void union(int a, int b);

  public boolean connected(int a, int b);

  public int root(int a);

}
