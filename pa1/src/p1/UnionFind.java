package src.p1;

public interface UnionFind {
  public void union(int p, int q);

  public boolean connected(int p, int q);

  public int root(int p);

}
