package sorting;

import java.util.ArrayList;
import java.util.List;

import static range.Range.range;

public class Insert {

//  /**
//   * In place sorting, hence
//   * @return void
//   *
//   * @param list
//   * @param <E>
//   */
//  public static <E extends Comparable<E>> void sort(List<E> list) {
//    for (int ix : range(1, list.size())) {
//      for (int jx : range(ix, 0, -1)) {
//        if (list.get(ix).compareTo(list.get(jx)) >= 0) continue;
//        var tmp = list.get(ix);
//        list.set(ix, list.get(ix - 1));
//        list.set(ix - 1, tmp);
//      }
//    }
//  }
//

  public static <E extends Comparable<E>> void sort(E[] arr) {
    for (int ix: range(0, arr.length)) {
      for (int jx : range(ix, 0, -1)) {
        if (jx < 0) continue;
        if (arr[jx - 1].compareTo(arr[jx]) < 0) break;
        var tmp = arr[jx];
        arr[jx] = arr[jx - 1];
        arr[jx - 1] = tmp;
      }
    }
  }

  public static void sort(int[] arr) {
    for (int ix: range(0, arr.length)) {
      for (int jx : range(ix, 0, -1)) {
        if (jx < 0) continue;
        if (arr[jx - 1] < arr[jx]) break;
        var tmp = arr[jx];
        arr[jx] = arr[jx - 1];
        arr[jx - 1] = tmp;
      }
    }
  }

  public static void sort(int[] arr, int low, int high) {
    for (int ix : range(low, high)) {
      for (int jx : range(ix, low, -1)) {
        if (jx < low) continue;
        if (arr[jx - 1] < arr[jx]) break;
        var tmp = arr[jx];
        arr[jx] = arr[jx - 1];
        arr[jx -1 ] = tmp;
      }
    }
  }
}
