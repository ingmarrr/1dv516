package src.core;

import java.util.ArrayList;
import java.util.List;

import static src.utils.Range.range;

public class WQUnionFind implements IUnionFind {
  private final List<Integer> ids;
  private final List<Integer> weights;

  public WQUnionFind(int N) {
    ids = new ArrayList<>();
    weights = new ArrayList<>();

    for (int i : range(N)) {
      ids.add(i);
      weights.add(1);
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

    if (weights.get(ra) < weights.get(rb)) {
      ids.set(ra, rb);
      weights.set(rb, weights.get(ra) + 1);
    } else {
      ids.set(rb, ra);
      weights.set(ra, weights.get(rb) + 1);
    }
  }

  public int root(int a) {
    while (a != ids.get(a)) {
      a = ids.get(a);
    }
    return a;
  }

  public WQUnionFind clone() {
    final WQUnionFind uf = new WQUnionFind(ids.size());
    uf.ids.addAll(ids);
    uf.weights.addAll(weights);
    return uf;
  }

  public List<Integer> getIds() {
    return ids;
  }

}
