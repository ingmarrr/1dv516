package src.core;

import java.util.List;

import static src.utils.Range.range;

public class PCUnionFind implements IUnionFind {
  final private List<Integer> ids;

  public PCUnionFind(int N) {
    ids = range(N).toList();
  }

  @Override
  public void union(int a, int b) {
    if (a >= this.ids.size() || b >= this.ids.size() || a < 0 || b < 0) {
      return;
    }

    final int ra = root(a);
    final int rb = root(b);
    ids.set(ra, rb);
  }

  @Override
  public boolean connected(int a, int b) {
    if (a >= this.ids.size() || b >= this.ids.size() || a < 0 || b < 0) {
      return false;
    }
    return root(a) == root(b);
  }

  @Override
  public int root(int a) {
    while (a != ids.get(a)) {
      ids.set(a, ids.get(ids.get(a)));
      a = ids.get(a);
    }
    return a;
  }

  @Override
  public List<Integer> getIds() {
    return ids;
  }

  @Override
  public PCUnionFind clone() {
    PCUnionFind uf = new PCUnionFind(this.ids.size());
    for (int i : range(ids.size())) {
      uf.ids.set(i, this.ids.get(i));
    }
    return uf;
  }
}
