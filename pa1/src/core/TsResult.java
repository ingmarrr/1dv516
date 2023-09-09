package src.core;

import java.util.List;

import src.logging.Logger;

public class TsResult {
  private final List<Triple<Integer>> result;
  private final Logger log = new Logger("TsResult");

  public TsResult(List<Triple<Integer>> result) {
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
    return has(new Triple<Integer>(i, j, k));
  }

  public boolean isEmpty() {
    return result.isEmpty();
  }
}
