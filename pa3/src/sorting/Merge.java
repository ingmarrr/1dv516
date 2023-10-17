package sorting;

import java.util.Arrays;
import java.util.List;

import static range.Range.range;

public class Merge {

  public static void sortIterative(int[] arr) {

    int size = 1;
    int len = arr.length;

    while (size <= len - 1) {
      int low = 0;

      while (low < len - 1) {

        int mid = Math.min(low + size - 1, len - 1);
        int hi = Math.min(low + 2 * size - 1, len - 1);
        int ln = mid - low + 1;
        int rn = hi - mid;

        int[] la = new int[ln];
        int[] ra = new int[rn];

        for (int lx : range(ln)) {
          la[lx] = arr[low + lx];
        }

        for (int rx : range(rn)) {
          ra[rx] = arr[mid + rx + 1];
        }

        merge(arr, low, la, ra);

        low += 2 * size;
      }

      size = 2 * size;
    }
  }

  public static void sortRecursive(int[] arr) {
    if (arr == null || arr.length <= 1) return;
    int mid = arr.length / 2;

    var la = Arrays.copyOfRange(arr, 0, mid);
    var ra = Arrays.copyOfRange(arr, mid, arr.length);

    sortRecursive(la);
    sortRecursive(ra);

    merge(arr, 0, la, ra);
  }

  private static void merge(int[] arr, int kx, int[] la, int[] ra) {
    int ix = 0, jx = 0;
    while (ix < la.length && jx < ra.length) {
      if (la[ix] < ra[jx]) {
        arr[kx++] = la[ix++];
      } else {
        arr[kx++] = ra[jx++];
      }
    }

    while (ix < la.length) {
      arr[kx++] = la[ix++];
    }

    while (jx < ra.length) {
      arr[kx++] = ra[jx++];
    }
  }

}
