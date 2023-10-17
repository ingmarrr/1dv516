package sorting;

import java.util.Arrays;
import java.util.Collections;

import static range.Range.range;

public class Quick {

  public static void sort(int[] arr, int depth) {
    boolean mustSort = sort(arr, 0, depth, 0, arr.length - 1);
    if (mustSort) Insert.sort(arr);
  }

  private static boolean sort(int[] arr, int depth, int allowedDepth, int low, int high) {
    if (depth == allowedDepth) return true;
    if (low < high) {
      int pivotIx = partition(arr, low, high);
      boolean lb = sort(arr, depth + 1, allowedDepth, low, pivotIx - 1);
      boolean rb = sort(arr, depth + 1, allowedDepth, pivotIx + 1, high);
      if (lb || rb) return true;
    }
    return false;
  }

  private static int partition(int[] arr, int low, int high) {
    int pivotIx = medianIx(arr, low, high);
    int pivot = arr[pivotIx];
    swap(arr, pivotIx, low);
    int ix = low, jx = high;

    while (true) {

      while (arr[ix++] < pivot && ix < high) {}
      while (arr[--jx] > pivot && jx > low) {}

      if (ix >= jx) break;
      swap(arr, ix, jx);
    }

    swap(arr, low, jx);
    return jx;
  }

  private static int medianIx(int[] arr, int low, int high) {
    int mid = low + (high - low) / 2;

    if (arr[mid] < arr[low]) {
      swap(arr, mid, low);
    }

    if (arr[mid] > arr[high]) {
      swap(arr, mid, high);
    }

    if (arr[low] > arr[high]) {
      swap(arr, low, high);
    }

    return mid;
  }

  private static void swap(int[] arr, int left, int right) {
    if (left == right) return;
    int tmp = arr[left];
    arr[left] = arr[right];
    arr[right] = tmp;
  }

}
