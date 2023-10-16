package sorting;

import java.util.ArrayList;
import java.util.List;

import static range.Range.range;

public class Insert {

  /**
   * In place sorting, hence
   * @return void
   *
   * @param list
   * @param <E>
   */
  public static <E extends Comparable<E>> void sort(List<E> list) {
    for (int ix : range(1, list.size())) {
      for (int jx : range(ix, 0, -1)) {
        if (list.get(ix).compareTo(list.get(jx)) >= 0) continue;
        var tmp = list.get(ix);
        list.set(ix, list.get(ix - 1));
        list.set(ix - 1, tmp);
      }
    }
  }

  public static <E extends Comparable<E>> void sort(E[] list) {
    for (int ix : range(1, list.length)) {
      for (int jx : range(ix, 0, -1)) {
        if (list[ix].compareTo(list[jx]) >= 0) continue;
        var tmp = list[ix];
        list[ix] = list[jx];
        list[jx] = tmp;
      }
    }
  }
}
