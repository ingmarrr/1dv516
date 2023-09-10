package src.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import src.utils.Triple;
import src.utils.ThreeSumResult;

import static src.utils.Range.range;

public class ThreeSum {

  public static ThreeSumResult brute(List<Integer> l) {
    final List<Triple<Integer>> result = new ArrayList<>();
    for (int i : range(l.size())) {
      for (int j : range(l.size())) {
        for (int k : range(l.size())) {
          if (l.get(i) + l.get(j) + l.get(k) == 0) {
            final Triple<Integer> t = new Triple<>(l.get(i), l.get(j), l.get(k));
            result.add(t);
          }
        }
      }
    }
    return new ThreeSumResult(result);
  }

  // Improved version of brute force algorithm
  // that avoids duplicate triples
  // ! AS STATED DOES NOT COUNT SINCE ITS STILL O(N3)
  // ! JUST HERE COS ... WHY NOT
  public static ThreeSumResult bruteI(List<Integer> l) {
    final List<Triple<Integer>> result = new ArrayList<>();
    for (int i : range(0, l.size())) {
      for (int j : range(i + 1, l.size())) {
        for (int k : range(j + 1, l.size())) {
          if (l.get(i) + l.get(j) + l.get(k) == 0) {
            final Triple<Integer> t = new Triple<>(l.get(i), l.get(j), l.get(k));
            result.add(t);
          }
        }
      }
    }
    return new ThreeSumResult(result);
  }

  public static ThreeSumResult cache(List<Integer> l) {
    final Set<Integer> cache = new HashSet<>();
    final List<Triple<Integer>> result = new ArrayList<>();

    for (int ax : range(l.size())) {
      for (int bx : range(l.size())) {
        if (bx == ax) {
          continue;
        }
        final int a = l.get(ax);
        final int b = l.get(bx);
        final int c = -(a + b);
        if (cache.contains(c)) {
          final Triple<Integer> tr = new Triple<>(a, b, c);
          result.add(tr);
        }
        cache.add(b);
      }
    }

    return new ThreeSumResult(result);
  }

  public static ThreeSumResult cacheI(List<Integer> l) {
    final Set<Integer> cache = new HashSet<>();
    final List<Triple<Integer>> result = new ArrayList<>();

    for (int a : l) {
      for (int b : cache) {
        final int c = -(a + b);
        if (cache.contains(c)) {
          final Triple<Integer> tr = new Triple<>(b, c, a);
          result.add(tr);
        }
      }
      cache.add(a);
    }

    return new ThreeSumResult(result);
  }

  public static ThreeSumResult twoP(List<Integer> l) {
    final List<Integer> sorted = l.stream().sorted().toList();
    final List<Triple<Integer>> result = new ArrayList<>();
    int fp = 0;
    int bp = l.size() - 1;

    for (int a : l) {
      while (fp < bp) {
        final int negA = -a;
        final int b = sorted.get(fp);
        final int c = sorted.get(bp);
        final int sum = b + c;

        if (sum < negA) {
          fp++;
        } else if (sum > negA) {
          bp--;
        } else {
          final Triple<Integer> tr = new Triple<>(a, b, c);
          result.add(tr);
          fp++;
          bp--;
        }

      }
      fp = 0;
      bp = l.size() - 1;
    }

    return new ThreeSumResult(result);
  }
}
