package src.core;

import java.util.List;

import static src.utils.Range.range;

public class QUnionFind implements IUnionFind {
  final private List<Integer> ids;

  public QUnionFind(int N) {
    ids = range(N).toList();
  }

  @Override
  public boolean connected(int a, int b) {
    if (a >= this.ids.size() || b >= this.ids.size() || a < 0 || b < 0) {
      return false;
    }
    return root(a) == root(b);
  }

  @Override
  public void union(int a, int b) {
    if (a >= this.ids.size() || b >= this.ids.size() || a < 0 || b < 0) {
      return;
    }
    int ra = this.root(a);
    int rb = this.root(b);
    this.ids.set(ra, rb);
  }

  @Override
  public int root(int a) {
    while (a != this.ids.get(a)) {
      a = this.ids.get(a);
    }
    return a;
  }

  @Override
  public QUnionFind clone() {
    QUnionFind uf = new QUnionFind(this.ids.size());
    for (int i : range(ids.size())) {
      uf.ids.set(i, this.ids.get(i));
    }
    return uf;
  }

  @Override
  public List<Integer> getIds() {
    return ids;
  }
}
