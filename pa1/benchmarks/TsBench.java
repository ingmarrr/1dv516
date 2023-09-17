package benchmarks;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.ArrayList;
import java.util.List;

import src.core.ThreeSum;
import src.benchmark.Benchmark;

import static src.utils.Range.range;

public class TsBench {

  public static void main(String[] args) {
    final Benchmark bm = new Benchmark();
    final int seed = 10;

    final int upper = 50_000;
    final int step = 500;
    final int reps = 50;
    final Function<Integer, List<Integer>> setup = setup(seed);
    final List<Integer> sizes = range(100, upper, step).toList();
    // bm.benchST("Threesum - Cache Improved", "cache_i_50k", setup,
    // ThreeSum::cacheI, sizes, reps);

    final int cacheUpper = 5_000;
    final int cacheStep = 500;
    final int cacheReps = 10;
    final Function<Integer, List<Integer>> cacheSetup = setup(seed);
    final List<Integer> cacheSizes = range(100, cacheUpper, cacheStep).toList();
    bm.benchST("Threesum - Cache", "cache_15k", cacheSetup, ThreeSum::cache, cacheSizes, cacheReps);

    final int bruteStep = 100;
    final int bruteUpper = 1000;
    final int bruteReps = 10;
    final List<Integer> bruteSizes = range(bruteStep, bruteUpper, bruteStep).toList();
    // bm.benchST("Threeum - Brute", "brute", setup, ThreeSum::brute, bruteSizes,

    final int twoPStep = 100;
    final int twoPUpper = 5000;
    final int twoPReps = 20;
    final List<Integer> twoPSizes = range(twoPStep, twoPUpper, twoPStep).toList();
    // bm.benchST("Threesum - Two Pointers", "two_p", setup, ThreeSum::twoP,
    // twoPSizes, twoPReps);

  }

  private static Function<Integer, List<Integer>> setup(int seed) {
    return (sz) -> {
      final List<Integer> l = new ArrayList<>();
      for (final int ignored : range(sz)) {
        final int rndInt = ThreadLocalRandom.current().nextInt(-seed, seed);
        l.add(rndInt);
      }
      return l;
    };
  }
}
