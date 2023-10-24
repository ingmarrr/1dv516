package sorting;

import java.lang.reflect.Array;
import java.util.*;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static range.Range.range;

public class Heap implements Iterable {

  private final static int CAP = 15;
  private int size;
  private final int d;
  private int[] bt;

  public int[] getBt() {
    int[] out = new int[bt.length - 1];
    System.arraycopy(bt, 1, out, 0, bt.length - 1);
    return out;
  }

  public Heap(int d, int[] arr) {
    this.d = d;
    bt = new int[arr.length + 1];
    size = arr.length;
    for (int i : range(arr.length)) {
      bt[i + 1] = arr[i];
    }
  }

  public Heap(int d, int[] arr, int low, int high) {
    this.d = d;
    bt = new int[high - low + 1];
    size = bt.length - 1;
    for (int i : range(low, high)) {
      bt[i - low + 1] = arr[i];
    }
  }

  public void sort() {
    int upper = size / d;
    while (upper >= 1) sink(upper--);
    while (size > 1) {
      var tmp = bt[1];
      bt[1] = bt[size];
      bt[size] = tmp;
      size--;
      sink(1);
    }
  }

  public Optional<Integer> delMax() {
    if (size > 0) {
      int max = bt[1];

      bt[1] = bt[size];
      bt[size] = max;

      size--;
      sink(1);
      return of(max);
    }
    return empty();
  }

  private void sink(int valx) {
    while (valx * d <= size) {
      int childix = valx * d;
      if (childix < size && bt[childix] < (bt[childix + 1])) childix++;
      if (!(bt[valx] < (bt[childix]))) break;
      final int tmp = bt[valx];
      bt[valx] = bt[childix];
      bt[childix] = tmp;
      valx = childix;
    }
  }

  public void print() {
    for (int i : range(size + 1)) {
      System.out.println(bt[i]);
    }
  }

  @Override
  public Iterator<Integer> iterator() {
    return new InorderIterator();
  }

  private class InorderIterator implements Iterator<Integer> {
    int ix = 0;

    @Override
    public boolean hasNext() {
      return ix < bt.length - 1;
    }

    @Override
    public Integer next() {
      return bt[++ix];
    }
  }
}
