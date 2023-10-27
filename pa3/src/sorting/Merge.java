package sorting;

import java.util.Arrays;
import java.util.List;

import static range.Range.range;

public class Merge {
  public static void sortIterative(int[] arr) {
    int len = arr.length;
    int[] auxiliary = new int[len];

    for (int size = 1; size < len; size *= 2) {
      for (int low = 0; low < len - size; low += 2 * size) {
        int mid = low + size - 1;
        int high = Math.min(low + 2 * size - 1, len - 1);
        merge(arr, auxiliary, low, mid, high);
      }
    }
  }

  public static void sortRecursive(int[] arr) {
    int[] aux = new int[arr.length];
    sortRecursive(arr, aux, 0, arr.length - 1);
  }

  private static void sortRecursive(int[] arr, int[] aux, int low, int hi) {
    if (hi <= low) return;
    int mid = low + (hi - low) / 2;
    sortRecursive(arr, aux, low, mid);
    sortRecursive(arr, aux, mid + 1, hi);
    merge(arr, aux, low, mid, hi);
  }

  private static void merge(int[] arr, int[] aux, int low, int mid, int high) {
    System.arraycopy(arr, low, aux, low, high - low + 1);

    int lowx = low, midx = mid + 1;
    for (int kx : range(low, high + 1)) {
      if (lowx > mid) arr[kx] = aux[midx++];
      else if (midx > high) arr[kx] = aux[lowx++];
      else if (aux[midx] < aux[lowx]) arr[kx] = aux[midx++];
      else arr[kx] = aux[lowx++];
    }
  }

}
