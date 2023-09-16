package src.core;

import java.util.ArrayList;
import java.util.List;

import static src.utils.Range.range;

public class WPCQUnionFind implements IUnionFind {
  private final List<Integer> ids;
  private final List<Integer> sizes;

  public WPCQUnionFind(int N) {
    ids = new ArrayList<>();
    sizes = new ArrayList<>();

    for (int i : range(N)) {
      ids.add(i);
      sizes.add(1);
    }
  }

  public boolean connected(int a, int b) {
    if (a < 0 || a >= ids.size() || b < 0 || b >= ids.size()) {
      return false;
    }
    return root(a) == root(b);
  }

  public void union(int a, int b) {
    if (a < 0 || a >= ids.size() || b < 0 || b >= ids.size()) {
      return;
    }
    final int ra = root(a);
    final int rb = root(b);

    if (ra == rb) {
      return;
    }

    if (sizes.get(ra) < sizes.get(rb)) {
      ids.set(ra, rb);
      sizes.set(rb, sizes.get(ra) + 1);
    } else {
      ids.set(rb, ra);
      sizes.set(ra, sizes.get(rb) + 1);
    }
  }

  public int root(int a) {
    while (a != ids.get(a)) {
      ids.set(a, ids.get(ids.get(a)));
      a = ids.get(a);
    }
    return a;
  }

  public WPCQUnionFind clone() {
    final WPCQUnionFind uf = new WPCQUnionFind(ids.size());
    uf.ids.addAll(ids);
    uf.sizes.addAll(sizes);
    return uf;
  }

  public List<Integer> getIds() {
    return ids;
  }
}
