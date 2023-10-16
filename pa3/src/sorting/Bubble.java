package sorting;

import java.util.List;

import static range.Range.range;

public class Bubble {

  public static <E extends Comparable<E>> void sort(List<E> list) {
    final int len = list.size();
    for (int ix : range(len)) {
      var swap = false;
      for (int jx : range(len - ix - 1)) {
        if (list.get(jx).compareTo(list.get(jx + 1)) > 0) {
          var tmp = list.get(jx);
          list.set(jx, list.get(jx + 1));
          list.set(jx + 1, tmp);
          swap = true;
        }
      }
      if (!swap) {
        break;
      }
    }
  }

  public static <E extends Comparable<E>> void sort(E[] list) {
    final int len = list.length;
    for (int ix : range(len)) {
      var swap = false;
      for (int jx : range(len - ix - 1)) {
        if (list[jx].compareTo(list[jx + 1]) > 0) {
          var tmp = list[jx];
          list[jx] = list[jx + 1];
          list[jx + 1] = tmp;
          swap = true;
        }
      }
      if (!swap) {
        break;
      }
    }
  }
}
