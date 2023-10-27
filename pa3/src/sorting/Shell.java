package sorting;

import static range.Range.range;

public class Shell {

  private static <E extends Comparable<E>> void sort(E[] arr, int gap) {
    for (int ix : range(gap, arr.length)) {
      var tmp = arr[ix];
      var jx = ix;
      while (jx >= gap && arr[jx - gap].compareTo(tmp) > 0) {
        arr[jx] = arr[jx - gap];
        jx -= gap;
      }
      arr[jx] = tmp;
    }
  }

  public static <E extends Comparable<E>> void sortStd(E[] arr) {
    for (int gap = arr.length / 2; gap > 0; gap /= 2) {
      sort(arr, gap);
    }
  }

  public static <E extends Comparable<E>> void sortHibbard(E[] arr) {
    int gap = 1;
    for (int k = 1; gap < arr.length; k++) gap = (int) Math.pow(2, k) - 1;
    gap /= 2;

    while (gap > 1) {
      sort(arr, gap);
      gap /= 2;
    }
  }

  public static <E extends Comparable<E>> void sortKnuth(E[] arr) {
    int gap = 1;
    while (gap < arr.length / 3) gap = 3 * gap + 1;
    while (gap > 1) {
      sort(arr, gap);
      gap /= 3;
    }
  }

}
