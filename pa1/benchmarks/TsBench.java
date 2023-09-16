package benchmarks;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.ArrayList;
import java.util.List;

import src.core.ThreeSum;
import src.logging.Logger;
import src.benchmark.Benchmark;
import src.benchmark.Snapshot;

import static src.utils.Range.range;

public class TsBench {

  public static void main(String[] args) {

    final Benchmark bm = new Benchmark();
    final Logger log = new Logger("TsBench");

    final int upperCI = 15_000;
    final int upperLow = 500;
    final int step = 10;
    final int reps = 100;
    final int seed = 10;
    final Function<Integer, List<Integer>> setup = setup(seed);

    final List<Integer> sizesLow = range(step, upperLow, step).toList();
    final List<Integer> sizes = range(step, upperCI, step).toList();
    final List<Integer> sizesLowLow = range(0, 500, 1).toList();

    // bm.bench("Threeum - Brute", "brute", setup, ThreeSum::brute, sizesLowLow,
    // 100);
    bm.bench("Threesum - Cache", "cache", setup, ThreeSum::cache, sizesLow, reps);
    // bm.bench("Threesum - Cache Improved", "cache_i", setup, ThreeSum::cacheI,
    // sizes, reps);
    // bm.benchST("Threesum - Two Pointers", "two_p", setup, ThreeSum::twoP,
    // sizesLow, reps);
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
