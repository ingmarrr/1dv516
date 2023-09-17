package src.core;

public class Percolation {
  private final int top;
  private final int bot;

  private final int n;
  private final int[] grid;
  private final WPCQUnionFind uf;

  private int openSites;

  public Percolation(int n) {
    if (n <= 0) {
      throw new IllegalArgumentException("n must be greater than 0");
    }

    this.n = n;
    top = n * n;
    bot = top + 1;
    openSites = 0;
    grid = new int[n * n];
    uf = new WPCQUnionFind(top + 2);
  }

  public boolean percolates() {
    return uf.connected(top, bot);
  }

  public void open(int row, int col) {
    if (!isValid(row, col)) {
      return;
    }

    if (isOpen(row, col)) {
      return;
    }

    grid[getIx(row, col)] = 1;
    openSites++;

    if (row == 0) {
      uf.union(getIx(row, col), top);
    }

    if (row == n - 1) {
      uf.union(getIx(row, col), bot);
    }

    if (isOpen(row - 1, col)) {
      uf.union(getIx(row, col), getIx(row - 1, col));
    }

    if (isOpen(row + 1, col)) {
      uf.union(getIx(row, col), getIx(row + 1, col));
    }

    if (isOpen(row, col - 1)) {
      uf.union(getIx(row, col), getIx(row, col - 1));
    }

    if (isOpen(row, col + 1)) {
      uf.union(getIx(row, col), getIx(row, col + 1));
    }
  }

  private boolean isOpen(int row, int col) {
    if (!isValid(row, col)) {
      return false;
    }

    return grid[getIx(row, col)] == 1;
  }

  private boolean isValid(int row, int col) {
    return row >= 0 && row < n && col >= 0 && col < n;
  }

  private int getIx(int row, int col) {
    return row * n + col;
  }

  public int getOpenSites() {
    return openSites;
  }
}
