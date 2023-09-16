package src.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import src.utils.Triple;
import src.utils.ThreeSumResult;

import static src.utils.Range.range;

public class ThreeSum {

  public static ThreeSumResult brute(List<Integer> l) {
    final List<Triple<Integer>> result = new ArrayList<>();
    for (int i : range(l.size())) {
      for (int j : range(l.size())) {
        for (int k : range(l.size())) {
          if (i == j || i == k || j == k) {
            continue;
          }
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
    //@formatter:off
    for (int i : range(0, l.size())) {                                            // N
      for (int j : range(i + 1, l.size())) {                                      // N * N
        for (int k : range(j + 1, l.size())) {                                    // N * N * N
          if (l.get(i) + l.get(j) + l.get(k) == 0) {                              // N * N * N 
            final Triple<Integer> t = new Triple<>(l.get(i), l.get(j), l.get(k)); // N * N * N
            result.add(t);                                                        // N * N * N
          }                                                                       // => N^3
        }
      }
    }
    //@formatter:on
    return new ThreeSumResult(result);
  }

  public static ThreeSumResult cache(List<Integer> l) {
    final Set<Integer> cache = new HashSet<>();
    final List<Triple<Integer>> result = new ArrayList<>();

    //@formatter:off
    for (int ax : range(l.size())) {                        // N
      for (int bx : range(l.size())) {                      // N * N
        if (bx == ax) {                                     // N * N
          continue;                                         // N * N
        }                                                   //
        final int a = l.get(ax);                            // N * N
        final int b = l.get(bx);                            // N * N
        final int c = -(a + b);                             // N * N
        if (cache.contains(c)) {                            // N * N
          final Triple<Integer> tr = new Triple<>(a, b, c); // N * N
          result.add(tr);                                   // N * N
        }                                                   //
      }                                                     
      cache.add(l.get(ax));                                 // N
                                                            // => N^2
    }
    //@formatter:on

    return new ThreeSumResult(result);
  }

  public static ThreeSumResult cacheI(List<Integer> l) {
    final List<Triple<Integer>> result = new ArrayList<>();
    final Set<Integer> cache = new HashSet<>();

    //@formatter:off
    for (int a : l) {                                       // N
      for (int b : cache) {                                 // N/2 * N = N^2/2 = N^2
        final int c = -(a + b);                             // N^2
        if (cache.contains(c)) {                            // N^2
          final Triple<Integer> tr = new Triple<>(b, c, a); // N^2
          result.add(tr);                                   // N^2
        }                                                   //
      }                                                     //
      cache.add(a);                                         // N
    }                                                       // => N^2
    //@formatter:on

    return new ThreeSumResult(result);
  }

  public static ThreeSumResult twoP(List<Integer> l) {
    final List<Triple<Integer>> result = new ArrayList<>();
    //@formatter:off
    final List<Integer> sorted = l.stream().sorted().toList(); // O(N log N)
    int fp = 0;                                             // O(1)
    int bp = l.size() - 1;                                  // O(1)

    for (int a : l) {                                       // O(N)
      while (fp < bp) {                                     // Best : O(N^2/2) |  Worst : O(N^2)
        final int negA = -a;                                // N^2
        final int b = sorted.get(fp);                       // N^2
        final int c = sorted.get(bp);                       // N^2
        final int sum = b + c;                              // N^2

        if (sum < negA) {                                   // N^2
          fp++;                                             // 0 to N^2
        } else if (sum > negA) {                            // ..
          bp--;                                             // ..
        } else {                                            // ..
          final Triple<Integer> tr = new Triple<>(a, b, c); // ..
          result.add(tr);                                   // ..
          fp++;                                             // ..
          bp--;                                             // ..
        }
      }
      fp = 0;                                               // N
      bp = l.size() - 1;                                    // N
    }                                                       // => O(N log N) * O(N^2) = O(N^2)
                                                            // => O(N^2) Since O(N^2) dominates
    //@formatter:off

    return new ThreeSumResult(result);
  }
}
