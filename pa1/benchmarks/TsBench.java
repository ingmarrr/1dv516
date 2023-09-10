package benchmarks;

import java.util.Random;
import java.util.function.Function;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import src.core.Ts;
import src.logging.Logger;
import src.benchmark.Benchmark;
import src.benchmark.Snapshot;

import static src.benchmark.Range.range;

public class TsBench {
  private final static Random rand = new Random();
  private final static Logger log = new Logger("BENCH ThreeSum");

  public static void main(String[] args) {

    final Benchmark bm = new Benchmark();

    final int upper = 3_000;
    final int step = 100;
    final int reps = 10;
    final int seed = 10;
    final Function<Integer, List<Integer>> setup = setup(seed);
    final List<Integer> sizes = range(step, upper, step).toList();

    final Map<Integer, Snapshot> cacheBench = bm.bench(
            "Cache", "cache",
            setup, Ts::cache, sizes, reps
    );
    final Map<Integer, Snapshot> cacheIBench = bm.bench(
            "Cache Improved", "cacheI",
            setup, Ts::cacheI, sizes, reps
    );
    final Map<Integer, Snapshot> twoP = bm.bench(
            "Two Pointers", "twoP",
            setup, Ts::twoP, sizes, reps
    );


  }

  private static Function<Integer, List<Integer>> setup(int seed) {
    return (sz) -> {
      final List<Integer> l = new ArrayList<>();
      for (final int i : range(sz)) {
        l.add(rand.nextInt(-seed, seed));
      }
      return l;
    };
  }
}
