package src.p1;

import java.util.ArrayList;
import java.util.List;

public class QUnionFind {
  private List<Integer> ids;
  // private Log log;

  public QUnionFind(int N) {
    ids = new ArrayList<Integer>();
    // log = new Log("QUnionFind");
    for (int i = 0; i < N; i++) {
      ids.add(i);
    }
  }

  public boolean connected(int a, int b) {
    if (a >= this.ids.size() || b >= this.ids.size() || a < 0 || b < 0) {
      return false;
    }
    return root(a) == root(b);
  }

  public void union(int a, int b) {
    // log.info("IDS", this.ids);
    int ra = this.root(a);
    int rb = this.root(b);
    // log.info(ra, rb);
    this.ids.set(ra, rb);
    // log.info("IDS", this.ids);
  }

  public int root(int a) {
    while (a != this.ids.get(a)) {
      a = this.ids.get(a);
    }
    return a;
  }

  public List<Integer> getIds() {
    return ids;
  }
}
