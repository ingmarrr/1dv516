package range;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Range implements Iterable<Integer> {

  private final int start;
  private final int end;
  private final int step;

  public Range(int start, int end, int step) {
    this.start = start;
    this.end = end;
    this.step = step;
  }

  public Range(int start, int end) {
    this(start, end, 1);
  }

  public Range(int end) {
    this(0, end, 1);
  }

  public static Range range(int start, int end, int step) {
    return new Range(start, end, step);
  }

  public static Range range(int start, int end) {
    return new Range(start, end, 1);
  }

  public static Range range(int end) {
    return new Range(0, end, 1);
  }

  @Override
  public Iterator<Integer> iterator() {
    final int max = end;
    return new Iterator<>() {
      private int i = start;

      @Override
      public boolean hasNext() {
        return step > 0 ? i < max : i > max;
      }

      @Override
      public Integer next() {
        final int res = i;
        i += step;
        return res;
      }
    };
  }

  public List<Integer> toList() {
    final List<Integer> res = new ArrayList<>();
    for (int i : this) {
      res.add(i);
    }
    return res;
  }
}
