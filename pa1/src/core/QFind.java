package src.core;

import java.util.List;

import static src.utils.Range.range;

public class QFind implements IUnionFind {
  final private List<Integer> ids;

  public QFind(int N) {
    ids = range(N).toList();
  }

  @Override
  public void union(int a, int b) {
    if (a >= this.ids.size() || b >= this.ids.size() || a < 0 || b < 0) {
      return;
    }
    final int ra = root(a);
    final int rb = root(b);
    for (int i : range(ids.size())) {
      if (i == ra) {
        ids.set(i, rb);
      }
    }
  }

  @Override
  public boolean connected(int a, int b) {
    if (a >= this.ids.size() || b >= this.ids.size() || a < 0 || b < 0) {
      return false;
    }
    return ids.get(a) == ids.get(b);
  }

  @Override
  public int root(int a) {
    return ids.get(a);
  }

  @Override
  public List<Integer> getIds() {
    return ids;
  }

  @Override
  public QFind clone() {
    QFind uf = new QFind(this.ids.size());
    for (int i : range(ids.size())) {
      uf.ids.set(i, this.ids.get(i));
    }
    return uf;
  }
}
