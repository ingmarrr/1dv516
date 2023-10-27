package sorting;

import java.util.Arrays;
import java.util.Collections;

import static range.Range.range;

public class Quick {

  public enum Fallback { Heap, Insert, None };

  public static void sort(int[] arr) {
    sort(arr, 0, Fallback.None, -1, 0, arr.length - 1);
  }

  public static void sort(int[] arr, int depth, Fallback fallback) {
    sort(arr, 0, fallback, depth, 0, arr.length - 1);
  }

  private static void sort(int[] arr, int depth, Fallback fallback, int allowedDepth, int low, int high) {
    if (depth == allowedDepth && depth >= 0) {
      switch (fallback) {
        case Heap -> {
          var hp = new Heap(2, arr, low, high + 1);
          hp.sort();
          System.arraycopy(hp.getBt(), 0, arr, low, high - low + 1);
        }
        case Insert -> Insert.sort(arr, low, high + 1);
      }
      return;
    }
    if (low < high) {
      int pivotIx = partition(arr, low, high);
      sort(arr, depth + 1, fallback, allowedDepth, low, pivotIx - 1);
      sort(arr, depth + 1, fallback, allowedDepth, pivotIx + 1, high);
    }
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
