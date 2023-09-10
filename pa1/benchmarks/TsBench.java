package benchmarks;

import java.util.Random;
import java.util.function.Function;
import java.util.ArrayList;
import java.util.List;

import src.core.ThreeSum;
import src.benchmark.Benchmark;

import static src.utils.Range.range;

public class TsBench {
  private final static Random rand = new Random();

  public static void main(String[] args) {

    final Benchmark bm = new Benchmark();

    final int upper = 3_000;
    final int step = 100;
    final int reps = 10;
    final int seed = 10;
    final Function<Integer, List<Integer>> setup = setup(seed);
    final List<Integer> sizes = range(step, upper, step).toList();

    bm.bench(
            "Cache", "cache",
            setup, ThreeSum::cache, sizes, reps
    );
    bm.bench(
            "Cache Improved", "cacheI",
            setup, ThreeSum::cacheI, sizes, reps
    );
    bm.bench(
            "Two Pointers", "twoP",
            setup, ThreeSum::twoP, sizes, reps
    );


  }

  private static Function<Integer, List<Integer>> setup(int seed) {
    return (sz) -> {
      final List<Integer> l = new ArrayList<>();
      for (final int ignored : range(sz)) {
        l.add(rand.nextInt(-seed, seed));
      }
      return l;
    };
  }
}
