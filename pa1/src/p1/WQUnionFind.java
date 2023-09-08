package src.p1;

import java.util.ArrayList;
import java.util.List;

public class WQUnionFind implements UnionFind {
  private final List<Integer> ids;
  private final List<Integer> sizes;

  public WQUnionFind(int N) {
    ids = new ArrayList<>();
    sizes = new ArrayList<>();

    for (int i = 0; i < N; i++) {
      ids.add(i);
      sizes.add(1);
    }
  }

  public boolean connected(int a, int b) {
    return root(a) == root(b);
  }

  public void union(int a, int b) {
    final int ra = root(a);
    final int rb = root(b);

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
}
