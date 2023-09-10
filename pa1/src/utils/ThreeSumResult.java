package src.utils;

import java.util.List;


public class ThreeSumResult {
  private final List<Triple<Integer>> result;

  public ThreeSumResult(List<Triple<Integer>> result) {
    this.result = result;
  }

  public String toString() {
    return result.toString();
  }

  public boolean has(Triple<Integer> triple) {
    for (Triple<Integer> t : result) {
      if (t.compareTo(triple) == 0) {
        return true;
      }
    }
    return false;
  }

  public boolean has(int i, int j, int k) {
    return has(new Triple<>(i, j, k));
  }

  public boolean isEmpty() {
    return result.isEmpty();
  }
}
