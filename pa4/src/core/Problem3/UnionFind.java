package core.Problem3;

import static range.Range.range;

public class UnionFind {

  private final int[] ids;
  private final int[] weights;

  public UnionFind(int n) {
    ids = new int[n];
    weights = new int[n];

    for (int i : range(n)) {
      ids[i] = i;
      weights[i] = 1;
    }
  }

  public void union(int a, int b) {
    if (a < 0 || b < 0 || a >= ids.length || b >= ids.length) {
      return;
    }

    int ra = root(a);
    int rb = root(b);
    if (ra == rb) return;

    if (weights[ra] < weights[rb]) {
      weights[ra] = rb;
      weights[rb] += weights[ra] + 1;
    } else {
      weights[rb] = ra;
      weights[ra] += weights[rb] + 1;
    }
  }

  public boolean connected(int a, int b) {
    if (a < 0 || b < 0 || a >= ids.length || b >= ids.length) {
      return false;
    }
    return root(a) == root(b);
  }

  private int root(int val) {
    while (val != ids[val]) {
      ids[val] = ids[ids[val]];
      val = ids[val];
    }
    return val;
  }

}
