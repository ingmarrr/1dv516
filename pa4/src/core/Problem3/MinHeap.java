package core.Problem3;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static range.Range.range;

public class MinHeap<E extends Comparable> {

  private final Class<E> clazz;
  private int d;
  private E[] internal;
  private int size;
  private int cap;

  public int[] getArr() {
    var out = new int[internal.length - 1];
    System.arraycopy(internal, 1, out, 0, internal.length - 1);
    return out;
  }

  public MinHeap(int d, Class<E> clazz) {
    this.d = d;
    this.clazz = clazz;
    this.internal = (E[]) Array.newInstance(clazz, 17);
    this.size = 0;
  }

  public MinHeap(int d, Class<E> clazz, E[] arr) {
    this.d = d;
    this.clazz = clazz;
    this.internal = (E[]) Array.newInstance(clazz, arr.length + 1);
    this.size = arr.length;
    System.arraycopy(arr, 0, internal, 1, arr.length);
    int upper = size / d;
    while (upper >= 1) sink(upper--);
  }

  public Optional<E> popMin() {
    if (size <= 0) return empty();
    var tmp = internal[1];
    internal[1] = internal[size];
    internal[size] = tmp;
    size--;
    sink(1);
    return of(tmp);
  }

  public void push(E val) {
    if (size + 1 >= internal.length) {
      var tmp = internal;
      internal = (E[]) Array.newInstance(clazz, internal.length * 2 + 1);
      System.arraycopy(tmp, 0, internal, 0, tmp.length);
    }
    internal[++size] = val;
    swim(size);
  }

  public void update(E val, E updated) {
    for (int ix : range(internal.length)) {
      if (eq(internal[ix], val)) {
        internal[ix] = updated;
      }
    }
  }

  public void updateWhere(Function<E, Boolean> func, E updated) {
    for (int ix : range(internal.length)) {
      if (internal[ix] == null) continue;
      if (func.apply(internal[ix])) {
        internal[ix] = updated;
        sink(ix);
        return;
      }
    }
    push(updated);
  }

  public Optional<E> get(E val) {
    for (int ix : range(internal.length)) {
      if (eq(internal[ix], val)) return of(internal[ix]);
    }
    return empty();
  }

  public void sort() {
    int upper = size / d;
    while (upper >= 1) sink(upper--);
    while (size > 1) {
      final E tmp = internal[1];
      internal[1] = internal[size];
      internal[size] = tmp;
      size--;
      sink(1);
    }
  }

  private void sink(int valueIndex) {
    while (valueIndex * d <= size) {
        int childIndex = valueIndex * d;
        if (childIndex < size && gt(internal[childIndex], internal[childIndex + 1])) childIndex++;
        if (gt(internal[childIndex], internal[valueIndex])) break;
        final E tmp = internal[valueIndex];
        internal[valueIndex] = internal[childIndex];
        internal[childIndex] = tmp;
        valueIndex = childIndex;
    }
  }

  private void swim(int valueIndex) {
    while (valueIndex > 1 && gt(internal[valueIndex / d], internal[valueIndex])) {
      final E tmp = internal[valueIndex];
      internal[valueIndex] = internal[valueIndex / d];
      internal[valueIndex / d] = tmp;
      valueIndex /= d;
    }
  }

  private boolean gt(E first, E second) {
    return first.compareTo(second) > 0;
  }

  private boolean eq(E first, E second) {
    return first.compareTo(second) == 0;
  }

  public boolean hasSome() {
    return size > 0;
  }

  @Override
  public String toString() {
    return "MinHeap: " + size + " [" + Arrays.toString(internal) + "]";
  }
}
